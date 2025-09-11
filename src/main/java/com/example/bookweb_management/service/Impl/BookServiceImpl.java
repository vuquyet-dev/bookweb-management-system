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
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepository.searchByTitleOrAuthorOrPublisher(keyword, pageable);

        return books.map(book -> {
            BookResponseDTO dto = new BookResponseDTO();
            dto.setId(book.getId());
            dto.setTitle(book.getTitle());
            dto.setAuthor(book.getAuthor());
            dto.setPublisher(book.getPublisher());
            dto.setPageCount(book.getPageCount());
            dto.setPrintType(book.getPrintType());
            dto.setLanguage(book.getLanguage());
            dto.setDescription(book.getDescription());
            dto.setUserId(book.getUser().getId());
            dto.setCategoryIds(book.getCategories().stream().map(Category::getId).toList());
            return dto;
        });
    }

    @Override
    public void booksExcelExport(HttpServletResponse httpServletResponse) throws IOException {
        List<Book> books = bookRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Books Info");

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Title");
        row.createCell(2).setCellValue("author");
        row.createCell(3).setCellValue("publisher");
        row.createCell(4).setCellValue("Page Count");
        row.createCell(5).setCellValue("Print Type");
        row.createCell(6).setCellValue("Language");
        row.createCell(7).setCellValue("Description");
        row.createCell(8).setCellValue("User ID");
        row.createCell(9).setCellValue("Category ID");

        int dataRowIndex = 1;

        for(Book book : books)
        {
            XSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(book.getId());
            dataRow.createCell(1).setCellValue(book.getTitle());
            dataRow.createCell(2).setCellValue(book.getAuthor());
            dataRow.createCell(3).setCellValue(book.getPublisher());
            dataRow.createCell(4).setCellValue(book.getPageCount());
            dataRow.createCell(5).setCellValue(book.getPrintType());
            dataRow.createCell(6).setCellValue(book.getLanguage());
            dataRow.createCell(7).setCellValue(book.getDescription());
            dataRow.createCell(8).setCellValue(book.getUser().getId());
            String categoryIds = Optional.ofNullable(book.getCategories())
                    .orElse(Collections.emptySet())
                    .stream()
                    .filter(Objects::nonNull)
                    .map(Category::getName)// trả về name thay vì id cho dễ nhận biết
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining(", "));
            dataRow.createCell(9).setCellValue(categoryIds);
            dataRowIndex++;
        }

        for(int i = 0; i < 10; i++)
        {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
        }

        ServletOutputStream sos = httpServletResponse.getOutputStream();
        workbook.write(sos);
        workbook.close();
        sos.close();

    }
}
