import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import img from '../images/bgregister.jpg';

function Register() {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    email: '',
  });
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        alert('Registration successful! Redirecting to Verify Email...');
        navigate('/verify-email', { state: { username: formData.username } });
      } else {
        alert('Registration failed!');
      }
    } catch (error) {
      alert('Error occurred during registration.');
    }
  };

  return (
    <div className="relative flex justify-center items-center min-h-screen">
      {/* Background Image */}
      <img
        className="absolute inset-0 w-full h-full object-cover z-0 "
        src={img}
        alt="Background"
      />

      {/* Form Container */}
      <div className="relative w-full max-w-sm p-6 bg-white rounded-lg shadow-md z-10">
        <h2 className="text-3xl font-bold text-center text-gray-700 mb-6">Register</h2>
        <form onSubmit={handleRegister} className="space-y-2">
          {/* Username Field */}
          <div>
            <label className="block text-lg font-medium text-gray-700">Username:</label>
            <input
              type="text"
              name="username"
              placeholder="Enter your username"
              value={formData.username}
              onChange={handleInputChange}
              className="w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          {/* Email Field */}
          <div>
            <label className="block text-lg font-medium text-gray-700">Email:</label>
            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleInputChange}
              className="w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          {/* Password Field */}
          <div>
            <label className="block text-lg font-medium text-gray-700">Password:</label>
            <input
              type="password"
              name="password"
              placeholder="Enter your password"
              value={formData.password}
              onChange={handleInputChange}
              className="w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          {/* Submit Button */}
          <button
            type="submit"
            className="w-full py-2 bg-green-500 text-white rounded-lg font-medium hover:bg-green-600 transition focus:outline-none focus:ring-2 focus:ring-green-400"
          >
            Register
          </button>
        </form>
      </div>
    </div>
  );
}

export default Register;
