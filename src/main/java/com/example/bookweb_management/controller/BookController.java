package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.bookdto.BookCreateDTO;
import com.example.bookweb_management.dto.bookdto.BookResponseDTO;
import com.example.bookweb_management.dto.bookdto.BookUpdateDTO;
import com.example.bookweb_management.dto.userdto.UserCreateDTO;
import com.example.bookweb_management.dto.userdto.UserResponseDTO;
import com.example.bookweb_management.dto.userdto.UserUpdateDTO;
import com.example.bookweb_management.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookResponseDTO> getAllBooks()
    {
        return bookService.getAllBooks();
    }

    @GetMapping("/{code}")
    public BookResponseDTO getBook(@PathVariable Long code)
    {
        return bookService.getBook(code);
    }

    @PostMapping
    public BookResponseDTO createBook(@RequestBody @Valid BookCreateDTO createDTO)
    {
        return bookService.createBook(createDTO);
    }

    @PutMapping("/{code}")
    public BookResponseDTO updateBook(@PathVariable Long code,@RequestBody @Valid BookUpdateDTO updateDTO)
    {
        return bookService.updateBook(code, updateDTO);
    }

    @DeleteMapping("/{code}")
    public void deleteBook(@PathVariable Long code)
    {
        bookService.deleteBook(code);
    }

    @GetMapping("/search")
    public Page<BookResponseDTO> search(@RequestParam(defaultValue = "") String keyword,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size)
    {
        return bookService.search(keyword, page, size);
    }
}
