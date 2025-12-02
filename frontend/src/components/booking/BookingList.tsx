import BookingCard from "./BookingCard";
import "@/styles/booking/bookingList.css";

const BookingList = () => {
  return (
    <div>
      <h2>Bookings</h2>

      <div className="BookingList-navbar">
        <button className="active">
          <span>🟦</span> All
        </button>
        <button>
          <span>🟨</span> Pending
        </button>
        <button>
          <span>🟩</span> Approved
        </button>
        <button>
          <span>🟥</span> Declined
        </button>
        <button>
          <span>⚪</span> Cancelled
        </button>
      </div>

      <BookingCard />
    </div>
  );
};

export default BookingList;
