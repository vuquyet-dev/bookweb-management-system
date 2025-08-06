package com.example.books_categories.service.impl;

import com.example.books_categories.dto.CategoryDTO;
import com.example.books_categories.entity.Category;
import com.example.books_categories.exception.ResourceNotFoundException;
import com.example.books_categories.mapper.CategoryMapper;
import com.example.books_categories.repository.CategoryRepository;
import com.example.books_categories.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper mapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO dto) {
        Category entity = mapper.toEntity(dto);
        return mapper.toDTO(categoryRepository.save(entity));
    }

    @Override
    public List<CategoryDTO> createCategories(List<CategoryDTO> dtos) {
        List<Category> entities = mapper.toEntities(dtos);
        List<Category> saved = categoryRepository.saveAll(entities);
        return mapper.toDTOs(saved);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return mapper.toDTOs(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapper.toDTO(category);
    }
}