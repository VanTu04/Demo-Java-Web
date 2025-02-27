package com.project.shopapp.services;

import com.project.shopapp.DTO.CategoryDTO;
import com.project.shopapp.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO category);
    void deleteCategory(long id);
}