import "../css/Home.css"
import Header from "../components/Header"
import Circles from "../svg/circles.svg"

function Home(){
    return(
        <div className="home">
            <Header />
            <div className="circles">
                <img src={Circles} alt="Circles"/>
            </div>
            <div className="home-text">
                <h1>CRIE E GERENCIE SEU<br/>BANCO DE DADOS NoSQL</h1>
                <p>
                    Lorem Ipsum is simply dummy text of the printing and typesetting industry. 
                    Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, 
                    when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                </p>
                <button>Criar banco de dados</button>
            </div>
        </div>
    )
}

export default Home