import React, { Component } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
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

class Home extends Component {
  
    render() {
        return (
          
          <div className="App" style={{backgroundColor:"#FFFAF0"}}>
            <StyleWrapper>
            <FullCalendar 
              defaultView="dayGridMonth" 
              plugins={[ dayGridPlugin, interactionPlugin ]}
              dateClick={this.handleDateClick}

              eventClick={this.handleEventClick}
              //events={productData}
              events={[
                //firebase불러오기
              ]}
              editable={true}
              droppable={true}
              selectable={true}

              locale='ko'
            />
            </StyleWrapper>
          </div>
        );
    }
   
    handleDateClick = (arg) => {

      var plan = prompt(arg.dateStr);
      if (plan) {
        calendar_data.doc(arg.dateStr).set( { date : arg.dateStr , title : `${plan}`})
        CalendarSpread();
      }
    }

    handleEventClick = (info) => {
      var del = confirm('삭제하시겠습니까?');
      if (del) {
        calendar_data.doc(info.event.startStr).delete();
      }
    }
}
export default Home;