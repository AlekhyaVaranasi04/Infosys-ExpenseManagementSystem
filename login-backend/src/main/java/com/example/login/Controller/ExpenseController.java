package com.example.login.Controller;

import com.example.login.Entity.Expense;
import com.example.login.Entity.User;
import com.example.login.Service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

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
