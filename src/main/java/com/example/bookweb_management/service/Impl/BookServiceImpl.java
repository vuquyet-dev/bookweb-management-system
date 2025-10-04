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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.IconUIResource;
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
            dto.setTitle(book.getTitle());
            dto.setAuthor(book.getAuthor());
            dto.setPublisher(book.getPublisher());
            dto.setPageCount(book.getPageCount());
            dto.setPrintType(book.getPrintType());
            dto.setLanguage(book.getLanguage());
            dto.setDescription(book.getDescription());
            dto.setCategoryIds(book.getCategories().stream().map(Category::getId).toList());
            return dto;
        });
    }@Override
    public void booksExcelExport(HttpServletResponse httpServletResponse) throws IOException {
        List<Book> books = bookRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Books Info");

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Title");
        row.createCell(1).setCellValue("author");
        row.createCell(2).setCellValue("publisher");
        row.createCell(3).setCellValue("Page Count");
        row.createCell(4).setCellValue("Print Type");
        row.createCell(5).setCellValue("Language");
        row.createCell(6).setCellValue("Description");
        row.createCell(7).setCellValue("Category Name");

        int dataRowIndex = 1;

        for(Book book : books)
        {
            XSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(book.getTitle());
            dataRow.createCell(1).setCellValue(book.getAuthor());
            dataRow.createCell(2).setCellValue(book.getPublisher());
            dataRow.createCell(3).setCellValue(book.getPageCount());
            dataRow.createCell(4).setCellValue(book.getPrintType());
            dataRow.createCell(5).setCellValue(book.getLanguage());
            dataRow.createCell(6).setCellValue(book.getDescription());
            String categoryIds = Optional.ofNullable(book.getCategories())
                    .orElse(Collections.emptySet())
                    .stream()
                    .filter(Objects::nonNull)
                    .map(Category::getName)// trả về name thay vì id cho dễ nhận biết
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining(", "));
            dataRow.createCell(7).setCellValue(categoryIds);
            dataRowIndex++;
        }

        for(int i = 0; i < 8; i++)
        {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
        }

        ServletOutputStream sos = httpServletResponse.getOutputStream();
        workbook.write(sos);
        workbook.close();
        sos.close();

    }

    @Override
    public void booksExcelImport(MultipartFile multipartFile) throws IOException {
        DataFormatter formatter = new DataFormatter();

        try(Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for(int i = 1; i <= sheet.getLastRowNum(); i++)
            {
                Row row = sheet.getRow(i);
                if(row == null) continue;

                Book book = new Book();
                book.setTitle(formatter.formatCellValue(row.getCell(0)));
                book.setAuthor(formatter.formatCellValue(row.getCell(1)));
                book.setPublisher(formatter.formatCellValue(row.getCell(2)));

                //Mặc định data trong excel kiểu text nên phải parse sang kiểu int để lưu vào db
                book.setPageCount(Integer.parseInt(formatter.formatCellValue(row.getCell(3))));

                book.setPrintType(formatter.formatCellValue(row.getCell(4)));
                book.setLanguage(formatter.formatCellValue(row.getCell(5)));
                book.setDescription(formatter.formatCellValue(row.getCell(6)));

                String categoryName = formatter.formatCellValue(row.getCell(7));
                if(categoryName != null && !categoryName.isEmpty())
                {
                    String[] names = categoryName.split(", ");
                    for(String name : names)
                    {
                        Category category = categoryRepository.findByName(name.trim()).orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + name));
                        if(category != null)
                        {
                            book.addCategory(category);
                        }
                    }
                }

                bookRepository.save(book);
            }
        }
    }


}
