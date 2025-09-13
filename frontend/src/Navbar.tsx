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
        <Link to={"./auth/Register.tsx"} className="link">
          Become a host
        </Link>
        <select name="" id="">
          <option value=""></option>
          <option value="">Help Center</option>
          <option value="">Become a Host</option>
          <option value="">Refer a Host</option>
          <option value="">Find a Co-Host</option>
          <option value="">Gift Cards</option>
          <option value="">Log in or Sign up</option>
        </select>
      </div>
    </nav>
  );
};

export default Navbar;
