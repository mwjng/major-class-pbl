import { useState, useEffect } from 'react';
import { collection, getDocs } from "firebase/firestore";
import React, { Component } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from "@fullcalendar/interaction";
import { firestore } from "../firebase_config"
import styled from "@emotion/styled";
import { render } from 'node-sass';


const calendar_data = firestore.collection("calendar_data");
const Home2 = () => {
    const [events, setEvents] = useState([]);
    const eventsCollectionRef = collection(db, "calendar_data");

    useEffect(() => {
        // 비동기로 데이터 받을준비
        const getEvents = async () => {
            // getDocs로 컬렉션안에 데이터 가져오기
            const data = await getDocs(eventsCollectionRef);
            setEvents(data.docs.map((doc) => ({ ...doc.data() })))
        }
        getEvents();
    }, [])
    return <div>{events}</div>;
};
export default Home2;