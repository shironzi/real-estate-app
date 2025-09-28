import { Link, useLocation, useNavigate } from "react-router-dom";
import { BsPersonCircle } from "react-icons/bs";
import "./styles/Navbar.css";
import { useEffect, useState } from "react";
import { logout } from "./utils/auth";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { UserContext, useUserData } from "./context/UserContext";

const Navbar = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const { userData } = useUserData();
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false);
  const [showModal, setShowModal] = useState<boolean>(false);
  const [message, setMessage] = useState<string>();

  const currentPage = location.pathname;

  const handleCloseModal = () => {
    setShowModal(!showModal);
  };

  const handleLogout = async () => {
    try {
      await logout();
      localStorage.removeItem("token");
      setIsLoggedIn(false);
      navigate("/");
    } catch (err: any) {
      setMessage(err.message);
      setShowModal(true);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) setIsLoggedIn(true);
  }, [currentPage]);

  return (
    <nav>
      <Modal
        open={showModal}
        onClose={handleCloseModal}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Success
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            {message}
          </Typography>
        </Box>
      </Modal>

      {/* Logo */}
      <Link to={"/"} className="logo link">
        Shironzi
      </Link>
      <div className="nav-mid">
        <Link to={"/"} className="link">
          Homes
        </Link>
        <Link to={"/experience"} className="link">
          Experience
        </Link>
        <Link to={"./services"} className="link">
          Services
        </Link>
      </div>

      <div className="nav-account" style={{}}>
        <Link to={"/register"} className="link">
          Become a host
        </Link>

        {isLoggedIn ? (
          <div
            className="dropdown"
            onClick={() => setIsDropdownOpen((prev) => !prev)}
          >
            <BsPersonCircle size={30} className="dropdown-icon" />
            {isDropdownOpen && (
              <ul className="dropdown-menu">
                <li>
                  <Link to="/profile" className="link">
                    Profile
                  </Link>
                </li>

                {userData.role === "OWNER" && (
                  <li>
                    <Link to="/bookings" className="link">
                      Manage Property
                    </Link>
                  </li>
                )}

                {userData.role === "OWNER" && (
                  <li>
                    <Link to="/bookings" className="link">
                      Property Bookings
                    </Link>
                  </li>
                )}

                {userData.role === "RENTER" && (
                  <li>
                    <Link to="/bookings" className="link">
                      My Bookings
                    </Link>
                  </li>
                )}
                <li>
                  <Link to="/settings" className="link">
                    Settings
                  </Link>
                </li>
                <li onClick={handleLogout} className="logout-btn">
                  Logout
                </li>
              </ul>
            )}
          </div>
        ) : (
          <Link to={"/register"} className="authentication link">
            Login / Signup
          </Link>
        )}
      </div>
    </nav>
  );
};

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

export default Navbar;
