package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.bookdto.BookCreateDTO;
import com.example.bookweb_management.dto.bookdto.BookResponseDTO;
import com.example.bookweb_management.dto.bookdto.BookUpdateDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface BookService {
    public List<BookResponseDTO> getAllBooks();
    public BookResponseDTO getBook(Long id);
    public BookResponseDTO createBook(BookCreateDTO createDTO);
    public BookResponseDTO updateBook(Long id, BookUpdateDTO updateDTO);
    public void deleteBook(Long id);
    public Page<BookResponseDTO> search(String keyword, int page, int size);

    public void booksExcelExport(HttpServletResponse httpServletResponse) throws IOException;
}
