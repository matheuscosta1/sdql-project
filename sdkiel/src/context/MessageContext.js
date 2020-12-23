import React, { useState } from "react"
import Message from "../components/Message"

const MessageContext = React.createContext()

function MessageContextProvider(props){
    const [messages, setMessages] = useState([])

    const createMessage = (title, message, type) => {
        const messageObj = { id: new Date().getTime(), title, message, type }
        setMessages([...messages, messageObj])
    }

    const deleteMessage = (index) => {
        messages.splice(index, 1)
        setMessages([...messages])
    }

    return(
        <MessageContext.Provider value={{messages, createMessage, deleteMessage}}>
            { props.children }
        </MessageContext.Provider>
    )
}

export { MessageContextProvider, MessageContext }