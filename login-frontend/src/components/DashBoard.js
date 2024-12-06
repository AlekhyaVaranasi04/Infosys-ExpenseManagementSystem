import React, { useState, useEffect } from "react";
import axios from "axios";

const Dashboard = () => {
  const [income, setIncome] = useState("");
  const [expenses, setExpenses] = useState([]);
  const [dailyExpenses, setDailyExpenses] = useState([]);
  const [monthlyExpenses, setMonthlyExpenses] = useState({});
  const [date, setDate] = useState("");
  const [month, setMonth] = useState("");
  const [balance, setBalance] = useState(0);

  useEffect(() => {
    fetchExpenses();
  }, []);

  const fetchExpenses = async () => {
    try {
      const response = await axios.get("/expenses", {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });
      setExpenses(response.data);
      calculateBalance(response.data);
    } catch (error) {
      console.error("Error fetching expenses", error);
    }
  };

  const fetchDailyExpenses = async () => {
    try {
      const response = await axios.get(`/expenses/day?date=${date}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });
      setDailyExpenses(response.data);
    } catch (error) {
      console.error("Error fetching daily expenses", error);
    }
  };

  const fetchMonthlyExpenses = async () => {
    try {
      const response = await axios.get(
        `/expenses/month?year=${new Date(month).getFullYear()}&month=${
          new Date(month).getMonth() + 1
        }`,
        {
          headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
        }
      );
      setMonthlyExpenses(response.data);
    } catch (error) {
      console.error("Error fetching monthly expenses", error);
    }
  };

  const calculateBalance = (expenses) => {
    const totalExpense = expenses.reduce(
      (sum, expense) => sum + parseFloat(expense.amount),
      0
    );
    setBalance(income - totalExpense);
  };

  return (
    <div>
      <h1>Welcome to Your Dashboard</h1>

      <div>
        <label>Income: </label>
        <input
          type="number"
          value={income}
          onChange={(e) => setIncome(e.target.value)}
        />
      </div>

      <h2>Balance: {balance}</h2>

      <div>
        <h3>Daily Expenses</h3>
        <input
          type="date"
          value={date}
          onChange={(e) => setDate(e.target.value)}
        />
        <button onClick={fetchDailyExpenses}>Get Daily Expenses</button>
        <ul>
          {dailyExpenses.map((expense) => (
            <li key={expense.id}>
              {expense.description} - {expense.amount}
            </li>
          ))}
        </ul>
      </div>

      <div>
        <h3>Monthly Expenses</h3>
        <input
          type="month"
          value={month}
          onChange={(e) => setMonth(e.target.value)}
        />
        <button onClick={fetchMonthlyExpenses}>Get Monthly Expenses</button>
        <ul>
          {Object.entries(monthlyExpenses).map(([date, total]) => (
            <li key={date}>
              {date} - {total}
            </li>
          ))}
        </ul>
      </div>

      <h3>All Expenses</h3>
      <ul>
        {expenses.map((expense) => (
          <li key={expense.id}>
            {expense.description} - {expense.amount} on {expense.date}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Dashboard;
