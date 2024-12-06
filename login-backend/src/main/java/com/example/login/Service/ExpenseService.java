package com.example.login.Service;

import com.example.login.Entity.Expense;
import com.example.login.Entity.User;
import com.example.login.Repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Add a new expense
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // Get all expenses for a specific user
    public List<Expense> getUserExpenses(User user) {
        return expenseRepository.findByUser(user);
    }

    // Get expenses within a specific date range
    public List<Expense> getExpensesByDateRange(User user, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    // Get expenses for a specific day
    public List<Expense> getExpensesForDay(User user, LocalDate date) {
        return expenseRepository.findByUserAndDate(user, date);
    }

    // Get expenses grouped by date for a specific month
    public Map<LocalDate, Double> getExpensesForMonth(User user, int year, int month) {
        List<Expense> expenses = expenseRepository.findByUserAndMonth(user, year, month);
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getDate,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    // Calculate total daily expenditure
    public Double getDailyExpenditure(User user, LocalDate date) {
        List<Expense> expenses = getExpensesForDay(user, date);
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    // Calculate total monthly expenditure
    public Double getMonthlyExpenditure(User user, int year, int month) {
        Map<LocalDate, Double> expenses = getExpensesForMonth(user, year, month);
        return expenses.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    // Delete an expense by ID
    public void deleteExpense(Long expenseId) {
        if (expenseRepository.existsById(expenseId)) {
            expenseRepository.deleteById(expenseId);
        } else {
            throw new IllegalArgumentException("Expense with ID " + expenseId + " does not exist.");
        }
    }
}
