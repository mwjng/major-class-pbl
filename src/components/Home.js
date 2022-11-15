import React, { Component } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from "@fullcalendar/interaction"

class Home extends Component {
    render() {
        return (
          <div className="App">
            <FullCalendar 
              defaultView="dayGridMonth" 
              plugins={[ dayGridPlugin, interactionPlugin ]}
              dateClick={this.handleDateClick}
            />
          </div>
        );
    }
    handleDateClick = (arg) => { // bind with an arrow function
      alert(arg.dateStr)
    }
}
export default Home;
