package com.example.bookweb_management.repository;

import com.example.bookweb_management.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c " +
            "WHERE LOWER(CAST(c.content AS string)) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Comment> searchByContent(@Param("keyword") String keyword, Pageable pageable);

}
