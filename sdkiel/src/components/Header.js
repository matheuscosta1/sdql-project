import { useState } from "react"
import { Link } from "react-router-dom"
import "../css/header.css"
import Logo from "../logos/sdkielFull.svg"
import { HiHome, HiOutlineHome } from "react-icons/hi"
import { TiFlashOutline } from "react-icons/ti"

function Header(props){
    const {withLogo = true, withHome = true} = props
    const [ hovering, setHovering ] = useState(false)

    return (
        <div className="header">
            <div className="left">
                {withLogo && <img src={Logo} alt="logo"></img>}
            </div>
            <nav>
                <Link to="/add">add</Link>
                <Link to="/get">get</Link>
                <Link to="/del">delete</Link>
                <Link to="/upd">update</Link>
                {withHome && 
                    <Link to="/" className="home-icon" onMouseEnter={() => setHovering(true)} onMouseLeave={() => setHovering(false)}>
                        <HiHome className={`home-hover-icon ${hovering? "hovering":""}`}/>
                        <HiOutlineHome/>
                    </Link>
                }
            </nav>
        </div>
    )
}

export default Header