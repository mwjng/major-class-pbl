import { Route, Routes } from "react-router-dom";
import Calendar from "./components/Calendar" //캘린더 
import Error from "./components/Error" //에러

const App = () => (
  <Routes>
    <Route path='/' element={<Calendar />}/>
    <Route path='*' element={<Error />}/>
  </Routes>
);

export default App;
