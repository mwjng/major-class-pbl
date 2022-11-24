import { firestore } from './firebase';
import { useEffect } from 'react';

const calendar_data = firestore.collection("calendar_data");

calendar_data.collection(calendar_data).get().then((cal_data) => {
        cal_data.forEach((doc)=>{
          console.log(doc.data())
        })
      });
      
  useEffect(() => {
    console.log(firestore);
  });
