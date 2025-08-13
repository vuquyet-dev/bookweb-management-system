package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.bookdto.BookCreateDTO;
import com.example.bookweb_management.dto.bookdto.BookResponseDTO;
import com.example.bookweb_management.dto.bookdto.BookUpdateDTO;
import com.example.bookweb_management.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    //CreateDTO -> Entity
    Book toEntity(BookCreateDTO dto);

    //UpdateDTO -> Entity
    Book toEntity(BookUpdateDTO dto);

    //Entity -> ResponseDTO
    BookResponseDTO toResponseDTO(Book entity);

    List<BookResponseDTO> toResponseDTOs(List<Book> entities);

}
