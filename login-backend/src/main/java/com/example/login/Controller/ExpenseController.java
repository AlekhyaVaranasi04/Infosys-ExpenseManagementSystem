package com.example.login.Controller;

import com.example.login.Entity.Expense;
import com.example.login.Entity.User;
import com.example.login.Service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Add a new expense
    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense, Principal principal) {
        User user = getUserFromPrincipal(principal);
        expense.setUser(user);
        Expense savedExpense = expenseService.addExpense(expense);
        return ResponseEntity.ok(savedExpense);
    }

    // Get all expenses for the logged-in user
    @GetMapping
    public ResponseEntity<List<Expense>> getUserExpenses(Principal principal) {
        User user = getUserFromPrincipal(principal);
        List<Expense> expenses = expenseService.getUserExpenses(user);
        return ResponseEntity.ok(expenses);
    }

    // Get expenses by date range
    @GetMapping("/by-date")
    public ResponseEntity<List<Expense>> getExpensesByDateRange(
            Principal principal,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        User user = getUserFromPrincipal(principal);
        List<Expense> expenses = expenseService.getExpensesByDateRange(user, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    // Get expenses for a specific day
    @GetMapping("/by-day")
    public ResponseEntity<List<Expense>> getExpensesByDay(
            Principal principal,
            @RequestParam LocalDate date) {
        User user = getUserFromPrincipal(principal);
        List<Expense> expenses = expenseService.getExpensesForDay(user, date);
        return ResponseEntity.ok(expenses);
    }

    // Get monthly expense report
    @GetMapping("/by-month")
    public ResponseEntity<Map<LocalDate, Double>> getMonthlyExpenses(
            Principal principal,
            @RequestParam int year,
            @RequestParam int month) {
        User user = getUserFromPrincipal(principal);
        Map<LocalDate, Double> monthlyExpenses = expenseService.getExpensesForMonth(user, year, month);
        return ResponseEntity.ok(monthlyExpenses);
    }

    // Get remaining balance for the day
    @GetMapping("/balance/day")
    public ResponseEntity<Double> getRemainingBalanceForDay(
            Principal principal,
            @RequestParam LocalDate date,
            @RequestParam Double income) {
        User user = getUserFromPrincipal(principal);
        Double dailyExpenditure = expenseService.getDailyExpenditure(user, date);
        Double remainingBalance = income - dailyExpenditure;
        return ResponseEntity.ok(remainingBalance);
    }

    // Get remaining balance for the month
    @GetMapping("/balance/month")
    public ResponseEntity<Double> getRemainingBalanceForMonth(
            Principal principal,
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam Double income) {
        User user = getUserFromPrincipal(principal);
        Double monthlyExpenditure = expenseService.getMonthlyExpenditure(user, year, month);
        Double remainingBalance = income - monthlyExpenditure;
        return ResponseEntity.ok(remainingBalance);
    }

    // Delete an expense
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    private User getUserFromPrincipal(Principal principal) {
        // TODO: Fetch the logged-in user from Principal. Replace with actual logic.
        return new User(); // Placeholder
    }
}
