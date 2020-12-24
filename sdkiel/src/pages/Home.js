import { useContext } from "react"
import Header from "../components/Header"
import Circles from "../svg/circles.svg"
import { Link } from "react-router-dom"
import { MessageContext } from "../context/MessageContext"

import "../css/Home.css"

function Home(){
    const { createMessage } = useContext(MessageContext)

    return(
        <div className="home">
            <Header withHome={false} />
            <div className="circles">
                <img src={Circles} alt="Circles"/>
            </div>
            <div className="home-text">
                <h1>MANAGE YOUR<br/>NoSQL DATABASE</h1>
                <p>
                    Manage a NoSQL database: add, get, delete and update your data in a fast and easy way. 
                </p>
                <Link to="/add"><button>Manage Database</button></Link>
            </div>
        </div>
    )
}

export default Home