import React, { Component } from 'react';
import { useState, useEffect} from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from "@fullcalendar/interaction";
import productData from "../data/product-data.json";
<<<<<<< Updated upstream
import {firestore} from "../firebase_config";
=======
import { collection, getDocs } from "firebase/firestore";
import {firestore} from "../firebase_config"
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
=======
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

const [events, setEvents] = useState([]);
const eventsCollectionRef = collection(db, "calendar_data");

useEffect(()=>{
  // 비동기로 데이터 받을준비
  const getEvents = async () => {
   // getDocs로 컬렉션안에 데이터 가져오기
    const data = await getDocs(eventsCollectionRef);
    // users에 data안의 자료 추가. 객체에 id 덮어씌우는거
    setEvents(data.docs.map((doc)=>({ ...doc.data()})))
  }
  getEvents();
},[])

>>>>>>> Stashed changes
class Home extends Component {
    render() {
        return (
          <div className="App" style={{backgroundColor:"#FFFAF0"}}>
            <StyleWrapper>
            <FullCalendar 
              defaultView="dayGridMonth" 
              plugins={[ dayGridPlugin, interactionPlugin ]}
              dateClick={this.handleDateClick}
<<<<<<< Updated upstream
              eventClick={this.handleEventClick}   
              events={productData}
=======
              eventClick={this.handleEventClick}
              //events={productData}
              events={events}
              editable={true}
              droppable={true}
              selectable={true}
>>>>>>> Stashed changes
              locale='ko'
            />
            </StyleWrapper>
          </div>
        );
    }
   
    handleDateClick = (arg) => {
<<<<<<< Updated upstream
      const calendar_data = firestore.collection("calendar_data");
      var a = prompt(arg.dateStr);
      calendar_data.add( { date : arg.dateStr , title : `${a}`})
    }

    handleEventClick = (info) => {
      console.log("")
=======
      var plan = prompt(arg.dateStr);
      if (plan) {
        calendar_data.doc(arg.dateStr).set( { date : arg.dateStr , title : `${plan}`})
      }
    }

    handleEventClick = (info) => {
      var del = confirm('삭제하시겠습니까?');
      if (del) {
        calendar_data.doc(info.event.startStr).delete();
      }
>>>>>>> Stashed changes
    }
}
export default Home;