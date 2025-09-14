import { Link } from "react-router-dom";
import Navbar from "../Navbar";

const Home = () => {
  return (
    <div className="bg-black">
      <Navbar />
      <h1>Home</h1>
      <Link to="/login">
        <h1>Login</h1>
      </Link>
    </div>
  );
};

export default Home;
