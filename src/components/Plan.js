import { firestore } from './firebase';
import { useEffect } from 'react';

const calendar_data = firestore.collection("calendar_data");
      calendar_data.doc("plan").get().then((doc) => {
        console.log(doc.data());
      });