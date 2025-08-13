package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.bookdto.BookCreateDTO;
import com.example.bookweb_management.dto.bookdto.BookResponseDTO;
import com.example.bookweb_management.dto.bookdto.BookUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    public List<BookResponseDTO> getAllBooks();
    public BookResponseDTO getBook(Long code);
    public BookResponseDTO createBook(BookCreateDTO createDTO);
    public BookResponseDTO updateBook(Long code, BookUpdateDTO updateDTO);
    public void deleteBook(Long code);
    public Page<BookResponseDTO> search(String keyword, int page, int size);
}
