import {useState} from 'react';
import {motion} from 'framer-motion';
import {AnimatePresence} from 'framer-motion';
import {useNavigate} from 'react-router-dom';
import {createAssessment} from '../../lib/api.js';
import './HealthForm.css';

const formQuestions = [
    {id:'weight', label:'What is your weight?', type:'number', unit:['lbs', 'kg'], helper:'Use your current body weight.'},
    {id:'height', label:'What is your height?', type:'number', unit:['ft/in', 'cm'], helper:"If you choose ft/in, enter your total inches for now. Example: 70 for 5'10\"."},
    {id:'age', label:'What is your age?', type:'number', helper:'This keeps the recommendations age-aware without adding more intake questions.'},
    {id:'goal', label:'What is your current goal?', type:'text', helper:'Examples: lose fat, build muscle, improve consistency, maintain weight.'}
];

const HealthForm = () => {
    const navigate = useNavigate();
    const [count, setCount] = useState(0);
    const [formData, setFormData] = useState({
        weight: '',
        weightUnit: 'lbs',
        height: '',
        heightUnit: 'ft/in',
        age: '',
        goal: '',
    });
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [error, setError] = useState('');

    const handleNext = async (e) => {
        e.preventDefault();
        setError('');

        if (count < formQuestions.length - 1){
            setCount(count + 1);
        } else {
            await submitData();
        }
    };

    const submitData = async () => {
        setIsSubmitting(true);

        try {
            const assessment = await createAssessment({
                weight: Number(formData.weight),
                weightUnit: formData.weightUnit,
                height: Number(formData.height),
                heightUnit: formData.heightUnit,
                age: Number(formData.age),
                goal: formData.goal.trim(),
            });

            sessionStorage.setItem('latestAssessmentId', assessment.id);
            navigate('/features', {state: {assessment}});
        } catch (requestError) {
            setError(requestError.message);
        } finally {
            setIsSubmitting(false);
        }
    };

    const currentQuestion = formQuestions[count];
    const currentValue = formData[currentQuestion.id];

    return (
        <div className='form-container'>
            <AnimatePresence mode='wait'>
                <motion.div
                    key={count}
                    initial={{opacity: 0, x: 20}}
                    animate={{opacity: 1, x: 0}}
                    exit={{opacity: 0, x: -20}}
                    transition={{duration: 0.4}}
                >
                <form className='content-container' onSubmit={handleNext}>
                    <h2 className='question'>{currentQuestion.label}</h2>
                    <div className='enter-container'>
                        <input
                            className='input-form'
                            type={currentQuestion.type}
                            value={currentValue}
                            min={currentQuestion.type === 'number' ? '1' : undefined}
                            step={currentQuestion.type === 'number' ? (currentQuestion.id === 'age' ? '1' : 'any') : undefined}
                            onChange={(e) => setFormData({...formData, [currentQuestion.id]: e.target.value})}
                            required
                        />
                        {currentQuestion.unit && (
                            <select
                                className='unit-select'
                                value={formData[currentQuestion.id + 'Unit']}
                                onChange={(e) => setFormData({...formData, [currentQuestion.id + 'Unit']: e.target.value})}
                            >
                                {currentQuestion.unit.map(unit => (<option key={unit} value={unit}>{unit}</option>))}
                            </select>
                        )}
                    </div>
                    <p className='helper-text'>{currentQuestion.helper}</p>
                    {error && <p className='form-error'>{error}</p>}
                    <button className='confirm-button' type='submit' disabled={isSubmitting}>
                        {count === formQuestions.length - 1 ? (isSubmitting ? 'Submitting...' : 'Generate Plan') : 'Enter'}
                    </button>
                </form>
                </motion.div>
            </AnimatePresence>
        </div>
    );
};

export default HealthForm
