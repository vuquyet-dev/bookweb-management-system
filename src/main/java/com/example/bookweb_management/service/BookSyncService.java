package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.bookdto.GoogleApiBooksResponse;
import com.example.bookweb_management.entity.Book;
import com.example.bookweb_management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BookSyncService {//Gọi api và lưu vào db
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=java";

    public void syncBooks() {
        GoogleApiBooksResponse response = restTemplate.getForObject(API_URL, GoogleApiBooksResponse.class);

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

                bookRepository.save(book);
            }
        }
    }
}
