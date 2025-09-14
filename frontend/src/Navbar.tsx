import { Link } from "react-router-dom";

import "./styles/Navbar.css";

const Navbar = () => {
  return (
    <nav>
      {/* Logo */}
      <Link to={"/"} className="logo link">
        Shironzi
      </Link>
      <div className="nav-mid">
        <Link to={"./Properties/Explore.tsx"} className="link">
          Homes
        </Link>
        <Link to={"./Properties/Explore.tsx"} className="link">
          Experiences
        </Link>
        <Link to={"./Properties/Explore.tsx"} className="link">
          Services
        </Link>
      </div>

      <div className="nav-account">
        <Link to={"/register"} className="link">
          Become a host
        </Link>
        <Link to={"/register"} className="authentication link">
          Login / Signup
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;
