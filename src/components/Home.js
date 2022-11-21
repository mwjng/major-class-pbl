import React, { Component } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from "@fullcalendar/interaction"
import productData from "../data/product-data.json";
import {firestore} from "../firebase_config"

class Home extends Component {
  
    render() {
        return (
          <div className="App">
            <FullCalendar 
              defaultView="dayGridMonth" 
              plugins={[ dayGridPlugin, interactionPlugin ]}
              dateClick={this.handleDateClick}
              eventClick={this.handleEventClick}
              //events={productData}
              events={[
                { title: 'event 1', date: '2022-11-01' },
                { title: 'event 2', date: '2022-11-02' }
              ]}
              locale='ko'
            />
          </div>
        );
    }
   
    handleDateClick = (arg) => {
      const calendar_data = firestore.collection("calendar_data");
      var a = prompt(arg.dateStr);
      calendar_data.add( { date : arg.dateStr , title : `${a}`})
    }

    handleEventClick = (info) => {
      console.log("")
    }
}
export default Home;
