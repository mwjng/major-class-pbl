import FullCalendar from '@fullcalendar/react';

import dayGridPlugin from '@fullcalendar/daygrid';
import React, { useState } from 'react';
import { Component } from 'react';
import interactionPlugin from "@fullcalendar/interaction";
import productData from "../data/product-data.json";
import {firestore} from "../firebase_config"
import styled from "@emotion/styled";
export const StyleWrapper = styled.div`

.fc-day-sun a {
  color: #FFD700;
  text-decoration: none;
}

.fc-day-sat a {
  color: #FFA500;
  text-decoration: none;
}
.fc td {
    background: #FFFAF0;
    color: #6F606E;
  }
.fc event{
  background: #FFFAF0;
}
`

const calendar_data = firestore.collection("calendar_data");
/*
const [eventsData, setEventsData] = useState({})
const getEventsData = () => { 
  firebase.firestore().collection("calendat_data").get().then((snapshot) => {
    const events = snapshot.docs.map(event => event.data());
    setEventsData(events)
    console.log(events)
  }).catch((e) => {
    console.log(e + "fetching error")
  })
}
useEffect(() => {
   getEventsData()
 },[])
*/
const today = new Date();
const Calendar = (props) => {
  const [visible, setVisible] = useState(false);
  const [date, setDate] = useState(today);
  const handleDateSelect = (newDate) => {
    setDate(newDate);
    setVisible(false);
  };


  const handleDateClick = (arg) => { // bind with an arrow function
    console.log(arg)
  }

  // 클릭 시 이벤트 정보 받아옴
  const handleEventClick = (clickInfo) => {
    console.log(clickInfo.event.id) // id 값 나옴    
    }

  return (
      <FullCalendar
        plugins={[dayGridPlugin,interactionPlugin]}
        initialView="dayGridMonth"
        selectable = {true}
        dateClick={handleDateClick}
        eventClick={handleEventClick}
        select={handleDateSelect}
        weekends={true}
        events={[]}
        height="90vh"
      />
  );
};

export default Calendar;