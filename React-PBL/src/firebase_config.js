// Import the functions you need from the SDKs you need
import firebase from "firebase/compat/app"
import { initializeApp } from "firebase/app";
import 'firebase/compat/firestore';

// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyC1rkYCpVJTcKz52gdjppiKQDsIyQxr9K8",
  authDomain: "react-calendar2.firebaseapp.com",
  projectId: "react-calendar2",
  storageBucket: "react-calendar2.appspot.com",
  messagingSenderId: "240787909954",
  appId: "1:240787909954:web:9e9dcb5c6d2434803b6cdb",
  measurementId: "G-RXJD9E72RB"
};

// Initialize Firebase
export default firebase.initializeApp(firebaseConfig);
export const firestore = firebase.firestore();
