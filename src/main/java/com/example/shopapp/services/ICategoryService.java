package com.example.shopapp.services;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);

    Category findById(long id);

    List<Category> getAllCategory();

    Category updateCategory(long id, CategoryDTO categoryDTO);

    void deleteCategory(long id);
}
