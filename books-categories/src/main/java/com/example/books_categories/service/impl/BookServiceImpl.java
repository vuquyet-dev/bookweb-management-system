package com.example.books_categories.service.impl;

import com.example.books_categories.dto.BookDTO;
import com.example.books_categories.entity.Book;
import com.example.books_categories.exception.ResourceNotFoundException;
import com.example.books_categories.mapper.BookMapper;
import com.example.books_categories.repository.BookRepository;
import com.example.books_categories.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper mapper;

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<BookDTO> getAllBooks() {
        return mapper.toDTOs(bookRepository.findAll());
    }

    @Override
    public BookDTO getBook(Long code) {
        Book book = bookRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException("Book not found with code: " + code));
        return mapper.toDTO(book);
    }

    @Override
    public BookDTO createBook(BookDTO dto) {
        Book entity = mapper.toEntity(dto);
        Book saved = bookRepository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public BookDTO updateBook(Long code, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with code: " + code));
        Book book = mapper.toEntity(bookDTO);
        // Cập nhật các trường từ DTO
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthors(bookDTO.getAuthors());
        existingBook.setPublisher(bookDTO.getPublisher());
        existingBook.setPageCount(bookDTO.getPageCount());
        existingBook.setPrintType(bookDTO.getPrintType());
        existingBook.setLanguage(bookDTO.getLanguage());
        existingBook.setDescription(bookDTO.getDescription());

        // Lưu và trả DTO
        Book updated = bookRepository.save(existingBook);
        return mapper.toDTO(updated);
    }


    @Override
    public void deleteBook(Long code) {
        bookRepository.deleteById(code);
    }

    @Override
    public Page<BookDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (keyword == null || keyword.trim().isEmpty()) {
            return bookRepository.findAll(pageable).map(mapper::toDTO);
        }

        return bookRepository.search(keyword, pageable).map(mapper::toDTO);
    }
}
