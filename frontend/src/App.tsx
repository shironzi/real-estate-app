import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
  Outlet,
} from "react-router-dom";
import Login from "./pages/auth/Login";
import Home from "./pages/Home/Home";
import Register from "./pages/auth/Register";
import Navbar from "./Navbar";

import "./styles/global.css";
import PropertyForm from "./pages/property/PropertyForm";
import PropertyImage from "./pages/property/PropertyImage";
import PropertyView from "./pages/property/PropertyView";
import PropertyReview from "./pages/property/PropertyReview";
import ManageProperty from "./pages/property/ManageProperty";

const ProtectedRoutes = () => {
  const token = localStorage.getItem("token");
  console.log(token);

  if (!token) return <Navigate to="/login" replace />;

  return <Outlet />;
};

function App() {
  return (
    <Router>
      <Navbar />
      <div className="container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          {/* Property Routes */}
          <Route element={<ProtectedRoutes />}>
            <Route path="/property/form" element={<PropertyForm />} />
            <Route path="/property/image" element={<PropertyImage />} />
            <Route path="/property/review" element={<PropertyReview />} />
            <Route path="/property/manage" element={<ManageProperty />} />
            <Route path="/property/edit/info/:id" element={<PropertyForm />} />
            <Route
              path="/property/edit/image/:id"
              element={<PropertyImage />}
            />
            <Route
              path="/property/edit/review/:id"
              element={<PropertyReview />}
            />
            <Route path="/property/:id" element={<PropertyView />} />
          </Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
