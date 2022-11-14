import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const Home = () => {
  const [date, setDate] = useState(new Date());
  return (
    <DatePicker selected={date} onChange={(date) => setDate(date)} withPortal />
  );
};

export default Home;
