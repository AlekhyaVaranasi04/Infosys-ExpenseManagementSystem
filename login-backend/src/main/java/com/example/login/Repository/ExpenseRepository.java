package com.example.login.Repository;

import com.example.login.Entity.Expense;
import com.example.login.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Find all expenses for a specific user
    List<Expense> findByUser(User user);

    // Find expenses for a specific user within a date range
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

    // Find expenses for a specific user on a specific date
    List<Expense> findByUserAndDate(User user, LocalDate date);

    // Find expenses for a specific user in a specific month and year
    @Query("SELECT e FROM Expense e WHERE e.user = :user AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<Expense> findByUserAndMonth(@Param("user") User user, @Param("year") int year, @Param("month") int month);

    // Find all expenses for a specific category
    List<Expense> findByCategoryId(Long categoryId);
}
