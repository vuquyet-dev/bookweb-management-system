package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.categorydto.CategoryCreateDTO;
import com.example.bookweb_management.dto.categorydto.CategoryResponseDTO;
import com.example.bookweb_management.dto.categorydto.CategoryUpdateDTO;
import com.example.bookweb_management.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    //CreateDTO to Entity
    Category toEntity(CategoryCreateDTO dto);

    //UpdateDTO to Entity
    Category toEntity(CategoryUpdateDTO dto);

    //Entity to ResponseDTO
    CategoryResponseDTO toResponseDTO(Category entity);

    //List Entity to ResponseDTO
    List<CategoryResponseDTO> toResponseDTOs(List<Category> entities);


}
