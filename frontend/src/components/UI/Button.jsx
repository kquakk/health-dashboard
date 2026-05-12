import './Button.css';
import {motion} from 'framer-motion';

const Button = ({ children, onClick, className, delay=0, duration=0, type='button', disabled=false}) => {
    return (
        <motion.span
            initial={{ opacity:  0, y: 0}}
            animate={{ opacity: 1, y: 0}}
            transition={{ duration: duration, ease: "easeOut", delay: delay}}
            viewport={{ once: true}}
        >
            <button type={type} onClick={onClick} className={className} disabled={disabled}>
                <div className='button-content'>
                    <div className='button-text'>{children}</div>
                </div>
            </button>
        </motion.span>
    );
};

export default Button
