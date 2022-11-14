/**
 * ch11/lec/proj/ch11-01-1
 * ./src/index.js
 */

// import react, react-dom/client, react-router-dom and module
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';

const element = document.getElementById('root')
const root = createRoot(element);
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
