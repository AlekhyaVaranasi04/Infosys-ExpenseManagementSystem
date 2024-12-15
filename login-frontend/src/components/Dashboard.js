import React, { useState } from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { AiOutlineDollarCircle, AiOutlineWallet, AiOutlinePlus } from 'react-icons/ai';
import { MdSavings, MdEdit, MdDelete } from 'react-icons/md';
import { IoPersonCircle } from 'react-icons/io5';
import { useNavigate } from 'react-router-dom';

ChartJS.register(ArcElement, Tooltip, Legend);

function Dashboard() {
  const navigate = useNavigate();
  const [expenses, setExpenses] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [newExpense, setNewExpense] = useState({ name: '', amount: '' });
  const [totalIncome, setTotalIncome] = useState(0);
  const [incomeEditOpen, setIncomeEditOpen] = useState(false);
  const [profileMenuOpen, setProfileMenuOpen] = useState(false);

  const pieData = {
    labels: expenses.map((expense) => expense.name),
    datasets: [
      {
        label: 'Expense Amount',
        data: expenses.map((expense) => expense.amount),
        backgroundColor: ['#ff6384', '#36a2eb', '#ffce56', '#4bc0c0','#1364cf','#e82009','#09e8e4','#bf1ae8'],
        hoverBackgroundColor: ['#ff6384', '#36a2eb', '#ffce56', '#4bc0c0','#1364cf','#e82009','#09e8e4','#bf1ae8'],
      },
    ],
  };

  const mostSpending = expenses.reduce((max, expense) =>
    expense.amount > max.amount ? expense : max, { name: 'None', amount: 0 }
  );

  const totalExpenses = expenses.reduce((total, expense) => total + expense.amount, 0);
  const totalSavings = totalIncome - totalExpenses;

  const toggleIncomeEdit = () => setIncomeEditOpen(!incomeEditOpen);
  const handleIncomeChange = (e) => setTotalIncome(e.target.value);
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewExpense((prev) => ({ ...prev, [name]: value }));
  };

  const handleDeleteExpense = (indexToDelete) => {
    setExpenses((prev) => prev.filter((_, index) => index !== indexToDelete));
  };

  const handleAddExpense = () => {
    if (newExpense.name && newExpense.amount) {
      setExpenses((prev) => [
        ...prev,
        { name: newExpense.name, amount: parseFloat(newExpense.amount) },
      ]);
      setModalOpen(false);
      setNewExpense({ name: '', amount: '' });
    }
  };

  const handleLogout = () => {
    navigate('/login');
  };

  return (
    <div className="flex min-h-screen bg-gradient-to-r from-blue-100 via-purple-100 to-pink-100">
      {/* Profile Section */}
      <div className="absolute top-6 right-6 flex items-center space-x-4 z-50">
  <div className="relative">
    <button
      onClick={() => setProfileMenuOpen(!profileMenuOpen)}
      className="flex items-center text-xl p-2 bg-gray-200 rounded-full"
    >
      <IoPersonCircle className="text-3xl text-blue-600" />
    </button>
    {profileMenuOpen && (
      <div className="absolute right-0 mt-2 w-40 bg-white border border-gray-300 rounded-lg shadow-lg z-50">
        <ul>
          <li className="p-2 hover:bg-gray-100 cursor-pointer">Profile</li>
          <li className="p-2 hover:bg-gray-100 cursor-pointer" onClick={handleLogout}>Logout</li>
        </ul>
      </div>
    )}
  </div>
</div>


      {/* Main Content */}
      <div className="flex-1 p-6">
        {/* Header */}
        <div className=" absolute text-3xl font-bold text-blue-600">
    Expense Track 

    <span className='text-orange-500 '> Pro</span>
    
  </div>
        
        <div className="mb-6 text-center">
         
          <h1 className="text-4xl font-bold text-gray-800">Dashboard</h1>
        </div>

        {/* Cards Section */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
          {/* Total Income Card */}
          <div className="p-8 bg-white border-2 border-green-500 rounded-xl shadow-lg transform transition duration-300 hover:scale-105 cursor-pointer flex items-center relative">
            <AiOutlineDollarCircle className="text-5xl text-blue-600 mr-4" />
            <div>
              <h3 className="text-xl font-semibold">Total Income</h3>
              <p className="text-3xl font-bold mt-2">${totalIncome}</p>
              {/* <p className="text-sm mt-1">2% vs last 30 days</p> */}
            </div>
            <button
              className="absolute top-2 right-2 p-1 bg-green-500 text-white rounded-full hover:bg-green-600"
              onClick={toggleIncomeEdit}
            >
              <MdEdit className="text-xl" />
            </button>
          </div>

          {/* Total Expense Card with Plus Icon */}
          <div className="p-8 bg-white border-2 border-red-500 rounded-xl shadow-lg transform transition duration-300 hover:scale-105 cursor-pointer flex items-center relative">
            <AiOutlineWallet className="text-5xl text-red-600 mr-4" />
            <div>
              <h3 className="text-xl font-semibold">Total Expense</h3>
              <p className="text-3xl font-bold mt-2">${totalExpenses.toFixed(2)}</p>
              {/* <p className="text-sm mt-1">2% vs last 30 days</p> */}
            </div>
            <button
              className="absolute top-2 right-2 p-1 bg-green-500 text-white rounded-full hover:bg-green-600"
              onClick={() => setModalOpen(true)}
            >
              <AiOutlinePlus className="text-xl" />
            </button>
          </div>

          {/* Total Savings Card */}
          <div className="p-8 bg-white border-2 border-yellow-500 rounded-xl shadow-lg transform transition duration-300 hover:scale-105 cursor-pointer flex items-center">
            <MdSavings className="text-5xl text-yellow-600 mr-4" />
            <div>
              <h3 className="text-xl font-semibold">Total Savings</h3>
              <p className="text-3xl font-bold mt-2">${totalSavings.toFixed(2)}</p>
              {/* <p className="text-sm mt-1">1% vs last 30 days</p> */}
            </div>
          </div>

          {/* Most Spending Card */}
          <div className="p-8 bg-white border-2 border-purple-500 rounded-xl shadow-lg transform transition duration-300 hover:scale-105 cursor-pointer flex items-center">
            <AiOutlineWallet className="text-5xl text-purple-600 mr-4" />
            <div>
              <h3 className="text-xl font-semibold">Most Spending</h3>
              <p className="text-3xl font-bold mt-2">{mostSpending.name}</p>
              <p className="text-2xl font-bold mt-1">${mostSpending.amount.toFixed(2)}</p>
            </div>
          </div>
        </div>

        {/* Report Overview */}
        <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-4">
          {/* Report Overview */}
          <div className="p-4 bg-white rounded-xl shadow-lg">
            <h3 className="font-semibold text-xl mb-4">Report Overview</h3>
            <div className="h-96 max-w-full flex items-center justify-center">
              <Pie data={pieData} options={{ responsive: true, maintainAspectRatio: true }} />
            </div>
          </div>

          {/* Recent Expenses */}
          <div className="bg-white p-4 rounded-xl shadow-lg">
            <h3 className="font-semibold text-xl mb-4">Recent Expenses</h3>
            <ul>
              {expenses.map((expense, index) => (
                <li key={index} className="flex justify-between items-center py-3 border-b">
                  <span>{expense.name}</span>
                  <div className="flex items-center space-x-4">
                    <span className="font-bold">${expense.amount.toFixed(2)}</span>
                    <button onClick={() => handleDeleteExpense(index)} className="text-red-500 hover:text-red-700">
                      <MdDelete className="text-xl" />
                    </button>
                  </div>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>

      {/* Modal for Adding New Expense */}
      {modalOpen && (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex justify-center items-center z-50">
          <div className="bg-white p-6 rounded-lg shadow-lg w-80">
            <h3 className="text-xl font-semibold mb-4">Add New Expense</h3>
            <form>
              <div className="mb-4">
                <label className="block text-sm font-semibold">Expense Name</label>
                <input
                  type="text"
                  name="name"
                  value={newExpense.name}
                  onChange={handleInputChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                  placeholder="Enter name"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-semibold">Amount</label>
                <input
                  type="number"
                  name="amount"
                  value={newExpense.amount}
                  onChange={handleInputChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                  placeholder="Enter amount"
                />
              </div>
              <div className="flex justify-between">
                <button
                  type="button"
                  className="bg-gray-500 text-white px-4 py-2 rounded-md"
                  onClick={() => setModalOpen(false)}
                >
                  Cancel
                </button>
                <button
                  type="button"
                  className="bg-green-500 text-white px-4 py-2 rounded-md"
                  onClick={handleAddExpense}
                >
                  Add Expense
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Modal for Editing Income */}
      {incomeEditOpen && (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex justify-center items-center z-50">
          <div className="bg-white p-6 rounded-lg shadow-lg w-80">
            <h3 className="text-xl font-semibold mb-4">Edit Total Income</h3>
            <input
              type="number"
              value={totalIncome}
              onChange={handleIncomeChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
              placeholder="Enter total income"
            />
            <div className="mt-4 flex justify-between">
              <button
                type="button"
                className="bg-gray-500 text-white px-4 py-2 rounded-md"
                onClick={toggleIncomeEdit}
              >
                Cancel
              </button>
              <button
                type="button"
                className="bg-green-500 text-white px-4 py-2 rounded-md"
                onClick={toggleIncomeEdit}
              >
                Save
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default Dashboard;
