import React, { Component } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from "@fullcalendar/interaction"
import productData from "../data/product-data.json";
import {firestore} from "../firebase_config"
import firebaseConfig from "../firebase_config"

class Home extends Component {
    render() {
        return (
          <div className="App">
            <FullCalendar 
              defaultView="dayGridMonth" 
              plugins={[ dayGridPlugin, interactionPlugin ]}
              dateClick={this.handleDateClick}
              eventClick={this.handleEventClick}
              events={productData}
              locale='ko'
            />
          </div>
        );
    }
   
    handleDateClick = (arg) => {
      prompt(arg.dateStr);
    }

    handleEventClick = (info) => {
      console.log(firebaseConfig);
    }
}
export default Home;
