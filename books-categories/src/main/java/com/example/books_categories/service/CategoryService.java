package com.example.books_categories.service;

import com.example.books_categories.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO dto);
    List<CategoryDTO> createCategories(List<CategoryDTO> dtos);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategory(Long id);
}