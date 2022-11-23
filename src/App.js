import { Route, Routes} from "react-router-dom";
import Home from "./components/Home";
import Home2 from "./components/Home2";

const App = () => (
  <Routes>
    <Route path='/' element={<Home />} />
    <Route path='/home2' element={<Home2 />} />

  </Routes>
);

export default App;
