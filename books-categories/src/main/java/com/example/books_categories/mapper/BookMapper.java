package com.example.books_categories.mapper;

import com.example.books_categories.dto.BookDTO;
import com.example.books_categories.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDTO(Book book);
    Book toEntity(BookDTO dto);

    List<BookDTO> toDTOs(List<Book> book);
    List<Book> toEntities(List<BookDTO> dto);
}
