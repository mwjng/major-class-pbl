import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import firebase from './firebase_config';


const element = document.getElementById('root')
const root = createRoot(element);
console.log(firebase);
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
