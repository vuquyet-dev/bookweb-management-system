package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.bookdto.BookCreateDTO;
import com.example.bookweb_management.dto.bookdto.BookResponseDTO;
import com.example.bookweb_management.dto.bookdto.BookUpdateDTO;
import com.example.bookweb_management.dto.bookdto.GoogleApiBooksResponse;
import com.example.bookweb_management.entity.Book;
import com.example.bookweb_management.service.BookService;
import com.example.bookweb_management.service.BookSyncService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller //response bằng api view hoặc json
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookSyncService bookSyncService;

    @GetMapping("/csrf-token") // Lấy value token
    @ResponseBody
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/booktest")//call api xem books trên view
    public String bookTest(Model model) {
        try {
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=java";

            // Gọi API -> map vào DTO
            GoogleApiBooksResponse response = restTemplate.getForObject(apiUrl, GoogleApiBooksResponse.class);

            List<Book> books = new ArrayList<>();
            if (response != null && response.getItems() != null) {
                for (GoogleApiBooksResponse.Item item : response.getItems()) {
                    GoogleApiBooksResponse.VolumeInfo vi = item.getVolumeInfo();

                    Book book = new Book();
                    book.setTitle(vi.getTitle());
                    book.setAuthor(vi.getAuthors() != null ? String.join(", ", vi.getAuthors()) : "Unknown");
                    book.setPublisher(vi.getPublisher());
                    book.setPageCount(vi.getPageCount() != null ? vi.getPageCount() : 0);
                    book.setPrintType(vi.getPrintType());
                    book.setLanguage(vi.getLanguage());
                    book.setDescription(vi.getDescription());

                    books.add(book);
                }
            }

            model.addAttribute("books", books);
        } catch (RestClientException ex) {
            model.addAttribute("error", "Can not load data");
        }

        return "book-list"; // trỏ tới templates/book-list.html
    }

    @GetMapping("/all")
    @ResponseBody //response bằng json khi dùng @Controller
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookCreateDTO createDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.createBook(createDTO));
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody @Valid BookUpdateDTO updateDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, updateDTO));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<Page<BookResponseDTO>> search(@RequestParam(defaultValue = "") String keyword,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(bookService.search(keyword, page, size));
    }

    @GetMapping("/export")
    @ResponseBody
    public ResponseEntity<String> booksExcelExport(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=books-list.xlsx";

        httpServletResponse.setHeader(headerKey, headerValue);

        bookService.booksExcelExport(httpServletResponse);
        return ResponseEntity.ok("Export successfully");
    }

    @PostMapping("/import")
    @ResponseBody
    public ResponseEntity<String> bookExcelImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        bookService.booksExcelImport(multipartFile);
        return ResponseEntity.ok("Import successfully");
    }
}
