
import { Route, Routes } from "react-router-dom";
import Calendar, {Error, Main} from "./components/Calendar"; //캘린더 
import Insert from "./components/Insert";


const App = () => (
  <Routes>
    <Route path='/' element={<Main />}/>
    <Route path='/calendar' element={<Calendar />}/>
    <Route path='/calendar/insert' element={<Insert/>}/>
    <Route path='*' element={<Error />}/>
  </Routes>
);
export default App;
