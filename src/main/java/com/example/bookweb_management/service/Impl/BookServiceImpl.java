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

import java.util.*;

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
        return bookMapper.toResponseDTOs(bookRepository.findAllWithCategories());
    }

    @Override
    public BookResponseDTO getBook(Long id) {
        Book book = bookRepository.findByIdWithCategories(id).orElseThrow(() -> new ResourceNotFoundException("Not found book with code: " + id));
        return bookMapper.toResponseDTO(book);
    }

    @Override
    public BookResponseDTO createBook(BookCreateDTO createDTO) {
        if (bookRepository.existsByTitle(createDTO.getTitle())) {
            throw new DuplicateTitleException("Title " + createDTO.getTitle() + " already exists");
        }

        User user = userRepository.findById(createDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + createDTO.getUserId()));

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(createDTO.getCategoryIds()));
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("Not found categories with ids: " + createDTO.getCategoryIds());
        }

        // Map DTO -> entity nhưng ignore categories/user trong mapper
        Book book = bookMapper.toEntity(createDTO);
        book.setUser(user);
        
        book.setCategories(categories);

        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(savedBook);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookUpdateDTO updateDTO) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found book with code: " + id));
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
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookResponseDTO> search(String keyword, int page, int size) {
        return null;
    }
}
