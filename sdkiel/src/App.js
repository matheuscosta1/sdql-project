import React, { useContext } from "react"
import Home from "./pages/Home"
import Message from "./components/Message"
import Add from "./pages/Add"
import Get from "./pages/Get"
import Del from "./pages/Del"
import Upd from "./pages/Upd"
import { Switch, Route } from "react-router-dom"
import { MessageContext } from "./context/MessageContext"

import "./App.css"

function App() {
  const { messages, deleteMessage } = useContext(MessageContext)

  return (
    <div className="app">
      <Switch>
        <Route exact path="/" component={Home}/>
        <Route path="/add" component={Add} />
        <Route path="/get" component={Get} />
        <Route path="/del" component={Del} />
        <Route path="/upd" component={Upd} />
      </Switch>

      {
        messages.map((messageObj, index) => <Message {...messageObj} key={index+messageObj.id} handleDelete={() => deleteMessage(index)}/>)
      }
    </div>
  );
}

export default App;
