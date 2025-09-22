import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/auth/Login";
import Home from "./pages/Home/Home";
import Register from "./pages/auth/Register";
import Navbar from "./Navbar";

import "./styles/global.css";
import PropertyForm from "./pages/property/PropertyForm";
import PropertyImage from "./pages/property/PropertyImage";

function App() {
  return (
    <Router>
      <Navbar />
      <div className="container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/property/info" element={<PropertyForm />} />
          <Route path="/property/images" element={<PropertyImage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
