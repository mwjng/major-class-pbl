/**
 * ch11/lec/proj/ch11-01-1
 * ./src/App.js
 */

import { Route, Routes } from 'react-router-dom';
import Home from "./components/Home";
import About from "./components/About";

const App = () => (
  <Routes>
    <Route path="/" element={<Home />} />
    <Route path="/about" element={<About />} />
  </Routes>
);

export default App;