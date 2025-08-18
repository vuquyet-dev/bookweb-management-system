package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.bookdto.BookCreateDTO;
import com.example.bookweb_management.dto.bookdto.BookResponseDTO;
import com.example.bookweb_management.dto.bookdto.BookUpdateDTO;
import com.example.bookweb_management.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    //CreateDTO -> Entity
    Book toEntity(BookCreateDTO dto);

    //UpdateDTO -> Entity
    Book toEntity(BookUpdateDTO dto);

    //Entity -> ResponseDTO
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.id", target = "categoryId")
    BookResponseDTO toResponseDTO(Book entity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.id", target = "categoryId")
    List<BookResponseDTO> toResponseDTOs(List<Book> entities);

}
