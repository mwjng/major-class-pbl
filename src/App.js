import { Route, Routes} from "react-router-dom";
import Calendar from "./components/Calendar";

const App = () => (
  <Routes>
    <Route path='/' element={<Calendar />} />
  </Routes>
);

export default App;
