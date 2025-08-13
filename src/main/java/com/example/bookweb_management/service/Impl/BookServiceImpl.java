package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.bookdto.BookCreateDTO;
import com.example.bookweb_management.dto.bookdto.BookResponseDTO;
import com.example.bookweb_management.dto.bookdto.BookUpdateDTO;
import com.example.bookweb_management.entity.Book;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.exception.book_exception.DuplicateTitleException;
import com.example.bookweb_management.mapper.BookMapper;
import com.example.bookweb_management.repository.BookRepository;
import com.example.bookweb_management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookResponseDTO> getAllBooks() {
        return bookMapper.toResponseDTOs(bookRepository.findAll());
    }

    @Override
    public BookResponseDTO getBook(Long code) {
        Book book = bookRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException("Not found book with code: " + code));
        return bookMapper.toResponseDTO(book);
    }

    @Override
    public BookResponseDTO createBook(BookCreateDTO createDTO) {
        if(bookRepository.existsByTitle(createDTO.getTitle()))
        {
            throw new DuplicateTitleException("Title " + createDTO.getTitle() + " already exists");
        }
        Book book = bookMapper.toEntity(createDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(savedBook);
    }

    @Override
    public BookResponseDTO updateBook(Long code, BookUpdateDTO updateDTO) {
        Book book = bookRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException("Not found book with code: " + code));
        book.setTitle(updateDTO.getTitle());
        book.setAuthor(updateDTO.getAuthor());
        book.setPublisher(updateDTO.getPublisher());
        book.setPageCount(updateDTO.getPageCount());
        book.setPrintType(updateDTO.getPrintType());
        book.setLanguage(updateDTO.getLanguage());
        book.setDescription(updateDTO.getDescription());
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(savedBook);
    }

    @Override
    public void deleteBook(Long code) {
        bookRepository.deleteById(code);
    }

    @Override
    public Page<BookResponseDTO> search(String keyword, int page, int size) {
        return null;
    }
}
