package com.example.login.Service;

import com.example.login.Entity.Expense;
import com.example.login.Entity.User;
import com.example.login.Repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    // Delete an expense by ID
    public void deleteExpense(Long expenseId) {
        if (expenseRepository.existsById(expenseId)) {
            expenseRepository.deleteById(expenseId);
        } else {
            throw new IllegalArgumentException("Expense with ID " + expenseId + " does not exist.");
        }
    }
}
