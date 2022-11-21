import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';

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


root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
