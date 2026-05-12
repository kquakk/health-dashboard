import React, {useState} from 'react';
import {Link} from 'react-router-dom';
import './NavigationBar.css';

function NavigationBar() {
    return (
        <header className="header">
            <a href="/" className="logo">Health Dashboard</a>

            <nav className="navbar">
                <Link id='/' to='/'>Home</Link>
                <Link id='/about' to='/about'>About</Link>
                <Link id='/features' to='/features'>Features</Link>
                <Link id='/login' to='/login'>Login</Link>
            </nav>
        </header>
    );
}

export default NavigationBar;