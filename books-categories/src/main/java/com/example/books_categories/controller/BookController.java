package com.example.books_categories.controller;

import com.example.books_categories.dto.BookDTO;
import com.example.books_categories.entity.Book;
import com.example.books_categories.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book")
@Validated
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    public List<BookDTO> getAllBooks()
    {
        return bookService.getAllBooks();
    }

    @GetMapping("/{code}")
    public BookDTO getBook(@PathVariable Long code)
    {
        return bookService.getBook(code);
    }

    @PostMapping
    public BookDTO createBook(@RequestBody @Valid BookDTO bookDTO)
    {
        return bookService.createBook(bookDTO);
    }

    @PutMapping("/{code}")
    public BookDTO updateBook(@PathVariable Long code, @Valid @RequestBody BookDTO bookDTO)
    {
        return bookService.updateBook(code, bookDTO);
    }

    @DeleteMapping("/{code}")
    public void deleteBook(@PathVariable Long code)
    {
        bookService.deleteBook(code);
    }

    @GetMapping("/search")
    public Page<BookDTO> search(@RequestParam(required = false) String keyword,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size)
    {
        return bookService.search(keyword, page, size);
    }
}
