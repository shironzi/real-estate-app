import { useState } from "react";
import { FaEye, FaEyeSlash } from "react-icons/fa6";

import "../styles/auth.css";
import "../styles/global.css";
import { login } from "../utils/auth";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const res = await login(email, password);
  };

  return (
    <div>
      <form onSubmit={onSubmit}>
        <input
          className="emailInput"
          type="email"
          name="email"
          value={email}
          onInput={(e) => setEmail((e.target as HTMLInputElement).value)}
          placeholder="Email"
          required
        />
        <div className="password_container">
          <input
            className="password_input"
            name="passwordInput"
            type={showPassword ? "text" : "password"}
            value={password}
            onInput={(e) => setPassword((e.target as HTMLInputElement).value)}
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

        <button>Login</button>
      </form>
    </div>
  );
};

export default Login;
