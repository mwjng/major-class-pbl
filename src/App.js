import { Route, Routes } from "react-router-dom";
import Home from "./components/Home" //캘린더 
import Error from "./components/Error" //에러

const App = () => (
  <Routes>
    <Route path='/' element={<Home />}/>
    <Route path='*' element={<Error />}/>
  </Routes>
);

export default App;
