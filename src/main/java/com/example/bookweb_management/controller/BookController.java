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

    @GetMapping("/{id}")
    public BookResponseDTO getBook(@PathVariable Long id)
    {
        return bookService.getBook(id);
    }

    @PostMapping
    public BookResponseDTO createBook(@RequestBody @Valid BookCreateDTO createDTO)
    {
        return bookService.createBook(createDTO);
    }

    @PutMapping("/{id}")
    public BookResponseDTO updateBook(@PathVariable Long id,@RequestBody @Valid BookUpdateDTO updateDTO)
    {
        return bookService.updateBook(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id)
    {
        bookService.deleteBook(id);
    }

    @GetMapping("/search")
    public Page<BookResponseDTO> search(@RequestParam(defaultValue = "") String keyword,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size)
    {
        return bookService.search(keyword, page, size);
    }
}
