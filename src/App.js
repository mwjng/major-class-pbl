import { Route, Routes } from "react-router-dom";
import Home from "./components/Home.js" //캘린더 페이지
import Error from "./components/Error.js" //에러페이지

const App = () => (
  <Routes>
    <Route path='/' element={<Home />}/>
    <Route path='*' element={<Error />}/>
  </Routes>
);

export default App;
