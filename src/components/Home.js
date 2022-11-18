import React, { Component } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';

class Home extends Component {
    render() {
        return (
          <div className="App">
            <FullCalendar 
              defaultView="dayGridMonth" 
              locale='ko'
              plugins={[ dayGridPlugin]}
            />
          </div>
        );
    }
}
export default Home;
