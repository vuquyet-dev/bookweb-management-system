package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.categorydto.CategoryCreateDTO;
import com.example.bookweb_management.dto.categorydto.CategoryResponseDTO;
import com.example.bookweb_management.dto.categorydto.CategoryUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    public List<CategoryResponseDTO> getAllCategories();
    public CategoryResponseDTO getCategory(Long id);
    public CategoryResponseDTO createCategory(CategoryCreateDTO createDTO);
    public CategoryResponseDTO updateCategory(Long id, CategoryUpdateDTO updateDTO);
    public void deleteCategory(Long id);
    public Page<CategoryResponseDTO> search(String keyword, int page, int size);
}
