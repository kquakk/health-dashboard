import {useEffect, useState} from 'react';
import {Link, useLocation} from 'react-router-dom';
import NavigationBar from '../UI/NavigationBar.jsx';
import {getAssessment} from '../../lib/api.js';
import './FeaturePage.css';

function FeaturePage() {
    const location = useLocation();
    const [assessment, setAssessment] = useState(location.state?.assessment ?? null);
    const [isLoading, setIsLoading] = useState(!location.state?.assessment);
    const [error, setError] = useState('');

    useEffect(() => {
        if (assessment) {
            return;
        }

        const assessmentId = sessionStorage.getItem('latestAssessmentId');

        if (!assessmentId) {
            setError('Submit your health form first so we can build your personalized dashboard.');
            setIsLoading(false);
            return;
        }

        let isMounted = true;

        const loadAssessment = async () => {
            try {
                const response = await getAssessment(assessmentId);

                if (isMounted) {
                    setAssessment(response);
                }
            } catch (requestError) {
                if (isMounted) {
                    setError(requestError.message);
                }
            } finally {
                if (isMounted) {
                    setIsLoading(false);
                }
            }
        };

        loadAssessment();

        return () => {
            isMounted = false;
        };
    }, [assessment]);

    return (
        <div className='feature-page'>
            <NavigationBar />

            <main className='feature-shell'>
                {isLoading && (
                    <section className='feature-hero'>
                        <p className='feature-eyebrow'>Building your dashboard</p>
                        <h1 className='feature-title'>We&apos;re pulling your latest assessment now.</h1>
                    </section>
                )}

                {!isLoading && error && (
                    <section className='feature-hero'>
                        <p className='feature-eyebrow'>Assessment needed</p>
                        <h1 className='feature-title'>{error}</h1>
                        <div className='feature-actions'>
                            <Link className='feature-link' to='/form'>Go to health form</Link>
                        </div>
                    </section>
                )}

                {!isLoading && assessment && (
                    <>
                        <section className='feature-hero'>
                            <p className='feature-eyebrow'>Personalized output</p>
                            <h1 className='feature-title'>Your wellness dashboard is ready.</h1>
                            <p className='feature-description'>{assessment.plan.focusHeadline}</p>

                            <div className='feature-actions'>
                                <Link className='feature-link' to='/form'>Update answers</Link>
                                <Link className='feature-link subtle' to='/about'>Learn more</Link>
                            </div>
                        </section>

                        <section className='metric-grid'>
                            <article className='metric-card'>
                                <span className='metric-label'>Goal</span>
                                <strong className='metric-value'>{assessment.profile.goal}</strong>
                            </article>
                            <article className='metric-card'>
                                <span className='metric-label'>BMI</span>
                                <strong className='metric-value'>{assessment.bmi} <small>{assessment.bmiCategory}</small></strong>
                            </article>
                            <article className='metric-card'>
                                <span className='metric-label'>Protein target</span>
                                <strong className='metric-value'>{assessment.plan.dailyProteinGrams}g/day</strong>
                            </article>
                            <article className='metric-card'>
                                <span className='metric-label'>Hydration target</span>
                                <strong className='metric-value'>{assessment.plan.dailyWaterLiters}L/day</strong>
                            </article>
                        </section>

                        <section className='feature-grid'>
                            <article className='feature-card'>
                                <h2>Profile snapshot</h2>
                                <ul>
                                    <li>Age: {assessment.profile.age}</li>
                                    <li>Weight: {assessment.profile.weightLbs} lbs / {assessment.profile.weightKg} kg</li>
                                    <li>Height: {assessment.profile.heightDisplay} / {assessment.profile.heightCm} cm</li>
                                </ul>
                            </article>

                            <article className='feature-card'>
                                <h2>Key insights</h2>
                                <ul>
                                    {assessment.insights.map((insight) => (
                                        <li key={insight}>{insight}</li>
                                    ))}
                                </ul>
                            </article>

                            <article className='feature-card wide'>
                                <h2>Next steps</h2>
                                <p className='feature-checkpoint'>{assessment.plan.weeklyCheckpoint}</p>
                                <ul>
                                    {assessment.nextSteps.map((step) => (
                                        <li key={step}>{step}</li>
                                    ))}
                                </ul>
                            </article>
                        </section>
                    </>
                )}
            </main>
        </div>
    );
}

export default FeaturePage;
