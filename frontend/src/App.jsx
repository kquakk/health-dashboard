import { Routes, Route } from 'react-router-dom';
import LandingPage from './components/Pages/LandingPage';
import LoginPage from './components/Pages/LoginPage';
import HealthForm from './components/Pages/HealthForm';
import FeaturePage from './components/Pages/FeaturePage';
import AboutPage from './components/Pages/AboutPage';
import './App.css';

function App() {

    return (
        <div className="App">
            <Routes>
                <Route path='/' element={<LandingPage/>} />
                <Route path='/login' element={<LoginPage/>} />
                <Route path='/form' element={<HealthForm/>} />
                <Route path='/features' element={<FeaturePage/>} />
                <Route path='/about' element={<AboutPage/>} />
            </Routes>
        </div>
    );
}


export default App;
