import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
<<<<<<< Updated upstream

const element = document.getElementById('root')
const root = createRoot(element);
=======
import {firestore} from './firebase_config';
import { computeSegEndResizable } from '@fullcalendar/common';


const element = document.getElementById('root')
const root = createRoot(element);
console.log(firestore);

const calendar_data = firestore.collection("calendar_data");

calendar_data.doc("plan").get().then((doc)=>{
  console.log(doc.data());
  console.log(doc.id);
})

>>>>>>> Stashed changes
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
