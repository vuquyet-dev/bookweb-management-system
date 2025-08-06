package com.example.books_categories.service;

import com.example.books_categories.dto.BookDTO;
import com.example.books_categories.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface BookService {
    public List<BookDTO> getAllBooks();
    public BookDTO getBook(Long code);
    public BookDTO createBook(BookDTO bookDTO);
    public BookDTO updateBook(Long code, BookDTO bookDTO);
    public void deleteBook(Long code);
    public Page<BookDTO> search(String keywork, int page, int size);
}
