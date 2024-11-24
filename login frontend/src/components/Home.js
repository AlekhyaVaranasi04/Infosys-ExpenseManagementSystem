import React from 'react';
import { Link } from 'react-router-dom';
import img from '../images/bg3.jpg';

function Home() {
  return (
    <div className="relative w-full h-screen">
      {/* Background Image */}
      <img className="w-full h-full object-cover absolute" src={img} alt="Background" />

      {/* Centered Container */}
      <div className="relative flex flex-col justify-center items-center h-full gap-y-8">
        {/* Hero Section */}
        <div className="text-center w-5/6 max-w-3xl p-10 bg-white/20 backdrop-blur-md text-black rounded-lg shadow-lg">
          <header>
            <h1 className="text-4xl mb-5 text-black">Welcome to EXPENSE MANAGEMENT</h1>
            <p className="text-lg mb-8 text-black">Your Expense will be tracked.</p>
            <div className="flex justify-center gap-4">
              <Link
                to="/register"
                className="px-6 py-3 bg-blue-500 text-white rounded-lg font-semibold hover:scale-105 transition"
              >
                Get Started
              </Link>
              <Link
                to="/login"
                className="px-6 py-3 bg-white text-blue-500 border-2 border-blue-500 rounded-lg font-semibold hover:bg-blue-500 hover:text-white transition"
              >
                Login
              </Link>
            </div>
          </header>
        </div>

        {/* Features Section */}
        <div className="bg-blue-400 w-5/6 max-w-5xl p-10 min-h-[300px] rounded-lg shadow-lg backdrop-blur-md">
          <section>
            <h2 className="text-3xl text-white mb-6">Why Choose Us?</h2>
            <div className="flex flex-wrap justify-around gap-8">
              <div className="max-w-xs p-6 bg-white rounded-lg shadow-md hover:scale-105 transition">
                <h3 className="text-xl font-semibold mb-4">Easy Scheduling</h3>
                <p className="text-gray-600">Plan your Budget with us.</p>
              </div>
              <div className="max-w-xs p-6 bg-white rounded-lg shadow-md hover:scale-105 transition">
                <h3 className="text-xl font-semibold mb-4">Seamless Experience</h3>
                <p className="text-gray-600">Enjoy a smooth and user-friendly interface tailored to your needs.</p>
              </div>
              <div className="max-w-xs p-6 bg-white rounded-lg shadow-md hover:scale-105 transition">
                <h3 className="text-xl font-semibold mb-4">Secure Platform</h3>
                <p className="text-gray-600">Your data is safe with us. Privacy and security are our priorities.</p>
              </div>
            </div>
          </section>
        </div>
      </div>
    </div>
  );
}

export default Home;
