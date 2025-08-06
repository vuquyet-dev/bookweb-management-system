package com.example.books_categories.controller;

import com.example.books_categories.dto.CategoryDTO;
import com.example.books_categories.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Thêm 1 category
    @PostMapping
    public CategoryDTO createCategory(@RequestBody @Valid CategoryDTO dto) {
        return categoryService.createCategory(dto);
    }

    // Thêm nhiều category cùng lúc
    @PostMapping("/batch")
    public List<CategoryDTO> createCategories(@RequestBody List<CategoryDTO> dtos) {
        return categoryService.createCategories(dtos);
    }

    // Lấy tất cả category
    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Lấy category theo ID
    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }
}

