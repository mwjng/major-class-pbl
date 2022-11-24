import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import firebaseconfig from './firebase_config.js';
import { computeSegEndResizable } from '@fullcalendar/common';


const element = document.getElementById('root')
const root = createRoot(element);
console.log(firebaseconfig);
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
