package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.categorydto.CategoryCreateDTO;
import com.example.bookweb_management.dto.categorydto.CategoryResponseDTO;
import com.example.bookweb_management.dto.categorydto.CategoryUpdateDTO;
import com.example.bookweb_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getCategory(@PathVariable Long id)
    {
        return categoryService.getCategory(id);
    }

    @PostMapping
    public CategoryResponseDTO createCategory(@RequestBody CategoryCreateDTO createDTO)
    {
        return categoryService.createCategory(createDTO);
    }

    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(@PathVariable Long id,@RequestBody CategoryUpdateDTO updateDTO)
    {
        return categoryService.updateCategory(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id)
    {
        categoryService.deleteCategory(id);
    }
    public Page<CategoryResponseDTO> search(String keyword, int page, int size)
    {
        return categoryService.search(keyword, page, size);
    }
}
