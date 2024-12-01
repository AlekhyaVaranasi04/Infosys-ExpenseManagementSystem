package com.example.login.Service;

import com.example.login.Entity.Category;
import com.example.login.Entity.User;
import com.example.login.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Add a new category
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Get all categories for a specific user
    public List<Category> getUserCategories(User user) {
        return categoryRepository.findByUser(user);
    }

    // Delete a category by ID
    public void deleteCategory(Long categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist.");
        }
    }
}
