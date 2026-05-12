import React from 'react';
import './LandingPage.css';
import NavigationBar from '../UI/NavigationBar.jsx';
import TextAnimation from '../UI/TextAnimation.jsx';
import Button from '../UI/Button.jsx';
import Login from '../UI/Login.jsx';
import {Link} from 'react-router-dom';

function LandingPage() {
    return (
        <div className="landing-container">
            <NavigationBar />
            <div className="hero-section">
                <TextAnimation
                    el="h1"
                    text="Wellness, Simplified"
                    className="hero-title"
                />
                <TextAnimation
                    el="p"
                    text="Take the stress out of staying healthy. We bring your daily habits and wellness goals into one calm space. See the big picture of your health, all at a single glance."
                    className="content"
                />
            </div>
            <Link id='form' to='/form'>
                <Button className='get-started-button' delay='1.5' duration='0.8'>Get Started</Button>
            </Link>
            <div className='login'>
                <Login delay='1.5'/>
            </div>
        </div>
    );
}

export default LandingPage;