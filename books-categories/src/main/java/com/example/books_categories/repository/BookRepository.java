package com.example.books_categories.repository;

import com.example.books_categories.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.authors) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.publisher) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.printType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Book> search(@Param("keyword") String keyword, Pageable pageable);
}
