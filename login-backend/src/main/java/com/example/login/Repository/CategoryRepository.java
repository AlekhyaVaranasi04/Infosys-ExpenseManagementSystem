package com.example.login.Repository;

import com.example.login.Entity.Category;
import com.example.login.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find all categories for a specific user (if categories are user-specific)
    List<Category> findByUser(User user);

    // Find a category by its name (optional use-case)
    List<Category> findByName(String name);
}
