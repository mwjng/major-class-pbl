import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import React, { useState } from 'react';
import { useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import '../style/style.css'
import { Component } from 'react';
import interactionPlugin from "@fullcalendar/interaction";
import { firestore } from "../firebase_config"

export const Main = () => { //시작페이지
    return(
      <div className='mainPage'>
        <h1>TEAM : 2 0 1 8</h1>
      <h1><Link to ="calendar">C A L E N D A R</Link></h1>
      </div>
    );
  }


export const Error = () => { //에러페이지
  const locations = useLocation();
  return (
    <div>
      <h3> not found at {locations.pathname}</h3>
    </div>
  );
}


const today = new Date();
const calendar_data = firestore.collection("calendar_data");

const Calendar = () => { //켈린더
  const [visible, setVisible] = useState(false);
  const [date, setDate] = useState(today);

  const [eventsData, setEventsData] = useState({})
  const getEventsData = () => {
    calendar_data.get().then((snapshot) => {
      const events = snapshot.docs.map(event => event.data());
      setEventsData(events)
    }).catch((e) => {
      console.log(e + "fetching error")
    })
  }
  useEffect(() => {
    getEventsData()
  }, [eventsData])

  const handleDateSelect = (newDate) => {
    setDate(newDate);
    setVisible(true);
  };

  // 클릭 시 이벤트 정보 받고 삭제 질문
  const handleEventClick = (clickInfo) => {
    console.log(clickInfo.event.id) // id 값 나옴    
    var del = confirm('삭제하시겠습니까?');
    if (del) {
      calendar_data.doc(clickInfo.event.startStr).delete();
    }
  }
  
  const handleDateClick = (arg) => { // 날짜누르면 일정 추가
    //<Modal open={modalopen} close={closeModal} header="Modal heading"></Modal>
    var event = prompt("일정을 입력하세요.",);
    if (event) {
      calendar_data.doc(arg.dateStr).set({ date: arg.dateStr, title: `${event}` })
      console.log("데이터가 추가되었습니다.")
    }
    else
      console.log("데이터가 null입니다. 추가되지 않았습니다.")
  }


  return (
      <FullCalendar
        plugins={[dayGridPlugin, interactionPlugin]}
        headerToolbar={{ //툴바 위치, 위젯
          left: "prev,next today",
          center: "title",
          right: "dayGridMonth,dayGridWeek,dayGridDay",
        }}
        selectable={true}
        dateClick={handleDateClick}
        eventClick={handleEventClick}
        select={handleDateSelect}
        editable={true}
        droppable={true}
        weekends={true}
        events={eventsData}
        locale='ko'
      />
  );
};

export default Calendar;