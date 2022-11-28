import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import React, { useState } from 'react';
import { useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import '../style/style.css'
import interactionPlugin from "@fullcalendar/interaction";
import { firestore } from "../firebase_config"

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
var color = "#BDB76B";
const today = new Date();
const calendar_data = firestore.collection("calendar_data");

const Calendar = () => {


  const [event_color, setColor] = useState(color);

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
      calendar_data.doc(arg.dateStr + event).set({ date: arg.dateStr, title: `${event}`, color: `${event_color}` })
      getEventsData()
      console.log("데이터가 추가되었습니다.")
      console.log(event_color);
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

      <div className='button'>
        <button onClick={() => setColor("#BDB76B")} style={{background:'#BDB76B', color:'white'}}>기본</button>
        <button onClick={() => setColor("#FF8E7F")} style={{background:'#FF8E7F', color:'white'}}>빨강</button>
        <button onClick={() => setColor("#FFCB6B")} style={{background:'#FFCB6B', color:'white'}}>주황</button>
        <button onClick={() => setColor("#A5EA89")} style={{background:'#A5EA89', color:'white'}}>초록</button>
        <button onClick={() => setColor("#89A5EA")} style={{background:'#89A5EA', color:'white'}}>하늘</button>
        <button onClick={() => setColor("#800000")} style={{background:'#800000', color:'white'}}>고동</button>
        <button onClick={() => setColor("#59227C")} style={{background:'#59227C', color:'white'}}>보라</button>
        <button onClick={() => setColor("#929292")} style={{background:'#929292', color:'white'}}>회색</button>
      </div>

      <div className='goHome'>
        <h1><Link to="/">홈 화면</Link></h1>
      </div>
    </React.Fragment >
  );
};

export default Calendar;