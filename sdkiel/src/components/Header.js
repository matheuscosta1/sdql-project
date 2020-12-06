import { Link } from "react-router-dom"
import "../css/header.css"
import Logo from "../logos/sdkielFull.svg"

function Header(props){
    const {withLogo = true} = props

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
            </nav>
        </div>
    )
}

export default Header