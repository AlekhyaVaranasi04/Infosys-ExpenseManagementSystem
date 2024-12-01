package com.example.login.Repository;

import com.example.login.Entity.Expense;
import com.example.login.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Find all expenses for a specific user
    List<Expense> findByUser(User user);

    // Find expenses for a specific user within a date range
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

    // Find all expenses for a specific category
    List<Expense> findByCategoryId(Long categoryId);
}

