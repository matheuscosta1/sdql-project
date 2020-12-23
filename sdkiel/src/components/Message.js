import { useState } from "react"
import useTimer from "../hooks/useTimer"
import "../css/Message.css"

const ANIMATION_TIME = 500

function Message(props){
    const { type = "success", title, message, handleClose = () => {}} = props
    const [show, setShow] = useState(true)
    
    const hideMessage = () => {
        if(show){
            setShow(false)
            setTimeout(() => handleClose, ANIMATION_TIME)
        }
    }

    return (
        <div className={`message ${show? "show":"hide"} ${type}`}>
            <div className="message-text">
                {title && <span className="title">{title}:</span>}
                <p>{message}</p>
            </div>
            <button onClick={hideMessage}>
                &times;
            </button>
        </div>
    )
}

export default Message