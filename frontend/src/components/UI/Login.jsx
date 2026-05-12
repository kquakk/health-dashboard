import {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import Button from './Button.jsx';
import './Login.css';
import {login} from '../../lib/api.js';

const Login = ({ delay=0 , className}) => {
    const navigate = useNavigate();
    const [credentials, setCredentials] = useState({username: '', password: ''});
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [feedback, setFeedback] = useState({error: '', success: ''});

    const handleChange = (field) => (event) => {
        setCredentials((prev) => ({...prev, [field]: event.target.value}));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setIsSubmitting(true);
        setFeedback({error: '', success: ''});

        try {
            const session = await login(credentials);
            sessionStorage.setItem('authSession', JSON.stringify(session));
            setFeedback({error: '', success: session.message});
            navigate(session.redirectPath ?? '/form');
        } catch (error) {
            setFeedback({error: error.message, success: ''});
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
            <form className={`login-container ${className ?? ''}`.trim()} onSubmit={handleSubmit}>
                <h1 className='login-title'>Sign In</h1>

                <div className='input'>
                    <input
                        type='text'
                        className='user-input'
                        placeholder='Username'
                        value={credentials.username}
                        onChange={handleChange('username')}
                        required
                    />
                    <input
                        type='password'
                        className='pass-input'
                        placeholder='Password'
                        value={credentials.password}
                        onChange={handleChange('password')}
                        required
                    />
                </div>

                {feedback.error && <p className='login-feedback error'>{feedback.error}</p>}
                {feedback.success && <p className='login-feedback success'>{feedback.success}</p>}

                <Button className='login-button' delay={delay} type='submit' disabled={isSubmitting}>
                    {isSubmitting ? 'Signing In...' : 'Login'}
                </Button>
            </form>
    );
};

export default Login
