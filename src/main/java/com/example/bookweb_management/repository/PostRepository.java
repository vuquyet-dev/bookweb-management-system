package com.example.bookweb_management.repository;

import com.example.bookweb_management.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(CAST(p.content AS string)) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Post> searchByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);
}
