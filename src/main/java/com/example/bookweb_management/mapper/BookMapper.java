package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.bookdto.BookCreateDTO;
import com.example.bookweb_management.dto.bookdto.BookResponseDTO;
import com.example.bookweb_management.dto.bookdto.BookUpdateDTO;
import com.example.bookweb_management.entity.Book;
import com.example.bookweb_management.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {
    // CreateDTO -> Entity
    @Mapping(target="id", ignore=true) // nếu create
    @Mapping(target="user", ignore=true)
    @Mapping(target="categories", ignore=true)
    Book toEntity(BookCreateDTO dto);

    // UpdateDTO -> Entity
    @Mapping(target="id", ignore=true) // nếu create
    @Mapping(target="user", ignore=true)
    @Mapping(target="categories", ignore=true)
    Book toEntity(BookUpdateDTO dto);

    // Entity -> ResponseDTO
    @Mapping(source = "id", target = "id") // ✨ map id từ entity sang dto
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "categories", target = "categoryIds", qualifiedByName = "mapCategories")
    BookResponseDTO toResponseDTO(Book entity);

    @Mapping(source = "id", target = "id") // ✨ map id
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "categories", target = "categoryIds", qualifiedByName = "mapCategories")
    List<BookResponseDTO> toResponseDTOs(List<Book> entities);

    // ✨ Custom mapper cho categories
    @Named("mapCategories")
    default List<Long> mapCategories(Set<Category> categories) {
        if (categories == null) return List.of();
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toList());
    }
}
