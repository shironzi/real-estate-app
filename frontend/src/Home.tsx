import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div>
      <h1>Home</h1>
      <Link to="/login">
        <h1>Login</h1>
      </Link>
    </div>
  );
};

export default Home;
