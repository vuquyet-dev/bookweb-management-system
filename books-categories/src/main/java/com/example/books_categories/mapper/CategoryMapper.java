package com.example.books_categories.mapper;

import com.example.books_categories.dto.CategoryDTO;
import com.example.books_categories.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO dto);

    List<CategoryDTO> toDTOs(List<Category> entities);
    List<Category> toEntities(List<CategoryDTO> dtos);
}

