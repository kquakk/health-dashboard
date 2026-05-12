import {motion} from "framer-motion";

const TextAnimation = ({text, el: Wrapper = "p", className}) => {
    const defaultVariants = {
        hidden: {opacity: 0, y: 50},
        visible: {opacity: 1, y: 0, transition: {duration: 0.8, ease: "easeOut"}},
    };

    return (
        <Wrapper className={className}>
            <motion.span
                initial="hidden"
                whileInView="visible"
                viewport={{ once: true}}
            >
                {text.split(" ").map((word, i) => (
                    <motion.span
                        key={i}
                        variants={defaultVariants}
                        style={{ display: "inline-block", marginRight: "0.25em"}}
                    >
                        {word}
                    </motion.span>
                ))}
            </motion.span>
        </Wrapper>
    );
};

export default TextAnimation