// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import firebase from 'firebase/compat/app';


// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyAwUAyreUB_KuEZW3JYRWB1PSIVyjXDNOw",
  authDomain: "calendar-2fb58.firebaseapp.com",
  projectId: "calendar-2fb58",
  storageBucket: "calendar-2fb58.appspot.com",
  messagingSenderId: "187835853696",
  appId: "1:187835853696:web:5796c416b57ec24d06ef65",
  measurementId: "G-BLN9N745ED",
};

// Initialize Firebase
export default firebase.initializeApp(firebaseConfig);
export const firestore = firebase.firestore;
