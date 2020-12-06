import Home from "./pages/Home"
import Add from "./pages/Add"
import Get from "./pages/Get"
import Del from "./pages/Del"
import Upd from "./pages/Upd"
import { Switch, Route } from "react-router-dom"

function App() {
  return (
    <div className="app">
      <Switch>
        <Route exact path="/" component={Home}/>
        <Route path="/add" component={Add} />
        <Route path="/get" component={Get} />
        <Route path="/del" component={Del} />
        <Route path="/upd" component={Upd} />
      </Switch>
    </div>
  );
}

export default App;
