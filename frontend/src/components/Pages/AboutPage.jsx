import React from 'react';
import NavigationBar from '../UI/NavigationBar.jsx';
import TextAnimation from '../UI/TextAnimation.jsx';
import './AboutPage.css';

function AboutPage() {
    return (
        <div className="landing-container">
            <NavigationBar />
                <div className="hero-section">
                    <TextAnimation
                        el="h1"
                        text="About Our App"
                        className="hero-title"
                    />
                    <TextAnimation
                        el="h2"
                        text="Your Journey, Quantified."
                        className="heading-about"
                    />
                    <TextAnimation
                        el="p"
                        text="We believe that fitness shouldn't be a guessing game. Most people fail their health goals not because of a lack of effort, but because of a lack of clarity. Our platform was built to bridge the gap between your daily actions and your long-term ambitions."
                        className="content"
                    />
                    <TextAnimation
                        el="h2"
                        text="Our Mission"
                        className="heading-about"
                    />
                    <TextAnimation
                        el="p"
                        text="To empower individuals with the tools they need to take ownership of their health. We remove the noise of generic fitness advice and replace it with a personalized roadmap based on your data, your goals, and your lifestyle."
                        className="content"
                    />
                    <TextAnimation
                        el="h2"
                        text="Built for the Modern Athlete"
                        className="heading-about"
                    />
                    <TextAnimation
                        el="p"
                        text="Whether you are calculating your first macro split or fine-tuning a professional training cycle, our app provides the technical backbone to support your sweat."
                        className="content"
                    />
                </div>
            </div>
    );
};

export default AboutPage