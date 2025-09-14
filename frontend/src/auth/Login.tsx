import { useEffect, useState } from "react";
import { FaEye, FaEyeSlash } from "react-icons/fa6";

import "../styles/auth.css";
import "../styles/global.css";
import { login } from "../utils/auth";
import { Link, useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const data = await login(email, password);
      navigate("/");
    } catch (err: any) {
      setError(err.message);
      e.preventDefault();
    }
  };

  // Redirect to homepage, if logged in
  useEffect(() => {
    const token = localStorage.getItem("token");

    if (token) {
      navigate("/");
    }
  }, []);

  return (
    <div>
      <h1 className="title text-center">Login</h1>
      {error.length && <h4 className="error">{error}</h4>}
      <form onSubmit={onSubmit}>
        <input
          className="input"
          type="email"
          name="email"
          value={email}
          onChange={(e) => setEmail((e.target as HTMLInputElement).value)}
          placeholder="Email"
          required
        />
        <div className="password_container">
          <input
            className="password_input"
            name="passwordInput"
            type={showPassword ? "text" : "password"}
            value={password}
            onChange={(e) => setPassword((e.target as HTMLInputElement).value)}
            placeholder="Password"
            required
          />
          <button
            type="button"
            onClick={togglePasswordVisibility}
            className="password_visibility"
          >
            {showPassword ? (
              <FaEye color="#e0e0e0" size={16} />
            ) : (
              <FaEyeSlash color="#e0e0e0" size={16} />
            )}
          </button>
        </div>

        <Link to="/register" className="auth-redirect">
          <h4>Don't have an account?</h4>
        </Link>
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;
