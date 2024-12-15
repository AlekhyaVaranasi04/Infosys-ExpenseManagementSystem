import React from 'react';
import { Link } from 'react-router-dom';
import img from '../images/bg3.jpg';

function Home() {
  return (
    <div className="relative w-full h-screen">
      {/* Background Image */}
      <img className="absolute inset-0 w-full h-screen " src={img} alt="Background" />

      {/* Overlay to Improve Text Visibility */}
      <div className="absolute inset-0 "></div>

      {/* Main Content */}
      <div className="relative flex flex-col justify-center items-center h-full px-4 ">
        {/* Hero Section */}
        <div className="text-left self-start ml-8">
          <h1 className="text-5xl font-bold mb-4 text-black">Welcome to <span className='text-blue-500 text-7xl'>ExpenseTrack <span className='text-orange-500 text-7xl'>Pro</span></span></h1>
          <p className="text-xl mb-6 text-black">Manage, track, and optimize your expenses effortlessly.</p>
          <div className="flex justify-start gap-4">
            <Link
              to="/register"
              className="px-6 py-3 bg-green-500 text-white rounded-full font-semibold hover:bg-green-600 transition"
            >
              Get Started
            </Link>
            <Link
              to="/login"
              className="px-8 py-3 bg-white text-green-500 border-2 border-green-500 rounded-full font-semibold hover:bg-green-500 hover:text-black transition"
            >
              Login
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
