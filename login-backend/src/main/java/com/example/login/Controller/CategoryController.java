package com.example.login.Controller;

import com.example.login.Entity.Category;
import com.example.login.Entity.User;
import com.example.login.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Add a new category
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category, Principal principal) {
        User user = getUserFromPrincipal(principal);
        category.setUser(user);
        Category savedCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    // Get all categories for the logged-in user
    @GetMapping
    public ResponseEntity<List<Category>> getUserCategories(Principal principal) {
        User user = getUserFromPrincipal(principal);
        List<Category> categories = categoryService.getUserCategories(user);
        return ResponseEntity.ok(categories);
    }

    // Delete a category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    private User getUserFromPrincipal(Principal principal) {
        // TODO: Fetch the logged-in user from Principal. Replace with actual logic.
        return new User(); // Placeholder
    }
}
