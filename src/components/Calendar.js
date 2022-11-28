import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import React, { useState } from 'react';
import { useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import '../style/style.css'
import interactionPlugin from "@fullcalendar/interaction";
import { firestore } from "../firebase_config"
import Modal from './Modal';

const goSelect = () => {
  document.location.href('/');
}

export const Main = () => { //시작페이지
  return (
    <div className='mainPage'>
      <h1>TEAM : 2 0 1 8</h1>
      <h1><Link to="calendar">캘린더 들어가기</Link></h1>
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
const Calendar = () => {

  const [signup, setSignup] = useState(false);

  const [visible, setVisible] = useState(false);
  const [date, setDate] = useState(today);

  const [eventsData, setEventsData] = useState({})
  const getEventsData = () => {
    calendar_data.get().then((snapshot) => {
      const events = snapshot.docs.map(event => event.data());
      setEventsData(events)
      console.log(events)
    }).catch((e) => {
      console.log(e + "fetching error")
    })
  }
  useEffect(() => {
    getEventsData();
  }, [])

  const handleDateSelect = (newDate) => {
    setDate(newDate);
    setVisible(true);
  };

  // 클릭 시 이벤트 정보 받아옴
  const handleEventClick = (clickInfo) => {
    console.log(clickInfo.event.id) // id 값 나옴    
    var del = confirm('삭제하시겠습니까?');
    if (del) {
      calendar_data.doc(clickInfo.event.startStr + clickInfo.event.title).delete();
      getEventsData();
      console.log("데이터가 삭제되었습니다.")
    }
  }

  const handleDateClick = (arg) => { // 날짜누르면 일정 추가
    var event = prompt("일정을 입력하세요.",);
    if (event) {
      calendar_data.doc(arg.dateStr + event).set({date: arg.dateStr, title: `${event}`, color: "" })
      getEventsData()
      console.log("데이터가 추가되었습니다.")
    }
    else
      console.log("데이터가 null입니다. 추가되지 않았습니다.")
  }

  return (
    <React.Fragment>
      <FullCalendar
        plugins={[dayGridPlugin, interactionPlugin]}
        headerToolbar={{
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
      <div className='goHome'>
        <h1><Link to="insert">일정 추가</Link> @ <Link to="/">홈 화면</Link></h1>
      </div>
    </React.Fragment>
  );
};

export default Calendar;