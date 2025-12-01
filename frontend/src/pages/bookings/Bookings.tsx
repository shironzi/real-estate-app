import "@/styles/booking/calendar.css";
import "@/styles/booking/bookings.css";
import BookingRequest from "../../components/booking/BookingRequest";
import BookingCalendar from "../../components/booking/BookingCalendar";
import BookingNavbar from "../../components/booking/BookingNavbar";
import { useState } from "react";
import { defaultTab, Tab } from "./BookingTypes";

const Bookings = () => {
  const [tab, setTab] = useState<Tab>(defaultTab);

  return (
    <div className="booking-container">
      <BookingNavbar tab={tab} onChange={setTab} />
      {tab.tab === "Calendar" && (
        <div className="booking-calendar-container">
          <BookingCalendar />
          <BookingRequest />
        </div>
      )}
    </div>
  );
};

export default Bookings;
