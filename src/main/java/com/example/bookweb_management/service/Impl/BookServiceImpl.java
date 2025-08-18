package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.bookdto.BookCreateDTO;
import com.example.bookweb_management.dto.bookdto.BookResponseDTO;
import com.example.bookweb_management.dto.bookdto.BookUpdateDTO;
import com.example.bookweb_management.entity.Book;
import com.example.bookweb_management.entity.Category;
import com.example.bookweb_management.entity.User;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.exception.book_exception.DuplicateTitleException;
import com.example.bookweb_management.mapper.BookMapper;
import com.example.bookweb_management.repository.BookRepository;
import com.example.bookweb_management.repository.CategoryRepository;
import com.example.bookweb_management.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        User user = userRepository.findById(createDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + createDTO.getUserId()));
        Category category = categoryRepository.findById(createDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Not found category with id: " + createDTO.getCategoryId()));
        Book book = bookMapper.toEntity(createDTO);
        book.setUser(user);
        book.setCategory(category);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(savedBook);
    }

    @Override
    public BookResponseDTO updateBook(Long code, BookUpdateDTO updateDTO) {
        Book book = bookRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException("Not found book with code: " + code));
        Book convert = bookMapper.toEntity(updateDTO);
        book.setTitle(convert.getTitle());
        book.setAuthor(convert.getAuthor());
        book.setPublisher(convert.getPublisher());
        book.setPageCount(convert.getPageCount());
        book.setPrintType(convert.getPrintType());
        book.setLanguage(convert.getLanguage());
        book.setDescription(convert.getDescription());
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
