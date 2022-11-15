import { Route, Routes } from "react-router-dom";
import Home from "./components/Home";
import Register from "./components/register";

const App = () => (
  <Routes>
    <Route path='/' element={<Home />} />
    <Route path='/register' element={<Register />} />
  </Routes>
);

export default App;
