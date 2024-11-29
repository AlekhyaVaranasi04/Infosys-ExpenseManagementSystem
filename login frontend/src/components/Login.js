import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../services/api';
import img from '../images/bgregister.jpg'

function Login() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post('/login', { username, password });
      setMessage(`${response.data} \nRedirecting to Profile...`);
      setTimeout(() => {
        navigate('/profile');
      }, 2000);
    } catch (error) {
      setMessage(error.response?.data || 'Login failed');
    }
  };

  return (
    <div className="relative flex items-center justify-center min-h-screen">
      <img
        className="absolute inset-0 w-full h-full object-cover z-0 transform scale-x-[-1]"
        src={img}
        alt="Background"
      />
      <div className="relative w-full max-w-md bg-white p-6 rounded-lg shadow-md">
        <h1 className="text-3xl font-bold text-gray-700 text-center mb-6">Login</h1>
        <form onSubmit={handleLogin} className="space-y-2">
          {/* Username Field */}
          <div>
            <label className="block text-lg font-medium text-gray-700">Username:</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Enter your username"
              className="w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          {/* Password Field */}
          <div>
            <label className="block text-lg font-medium text-gray-700">Password:</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Enter your password"
              className="w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          {/* Submit Button */}
          <button
            type="submit"
            className="w-full py-2 bg-green-500 text-white rounded-lg font-semibold hover:bg-green-600 transition focus:outline-none focus:ring-2 focus:ring-green-400"
          >
            Login
          </button>
        </form>
        {/* Footer Links */}
        <div className="mt-4 flex justify-between text-sm">
          <Link
            to="/forgot-password"
            className="text-purple-700 hover:underline"
          >
            Forgot Password?
          </Link>
          <Link
            to="/register"
            className="text-purple-700 hover:underline"
          >
            Don't have an account?
          </Link>
        </div>
        {/* Message */}
        {message && (
          <p className="mt-4 text-center text-sm text-red-500">{message}</p>
        )}
      </div>
    </div>
  );
}

export default Login;
