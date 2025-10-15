package com.example.bookweb_management.repository;

import com.example.bookweb_management.entity.BorrowRecord;
import com.example.bookweb_management.enums.BorrowStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    @Query("SELECT b FROM BorrowRecord b " +
            "WHERE(:userId IS NULL OR b.user.id = :userId)" +
            " AND(:bookId IS NULL OR b.book.id = :bookId)" +
            " AND(:status IS NULL OR LOWER(b.status) LIKE LOWER(CONCAT('%', :status, '%')))" +
            " AND(:startBorrowDate IS NULL OR b.borrowDate >= :startBorrowDate)" +
            " AND(:endBorrowDate IS NULL OR b.borrowDate <= :endBorrowDate)" +
            " AND(:startReturnDate IS NULL OR b.returnDate >= :startReturnDate)" +
            " AND(:endReturnDate IS NULL OR b.returnDate <= :endReturnDate)")
    Page<BorrowRecord>search(@Param("userId") Long userId,
                              @Param("bookId") Long bookId,
                              @Param("status")BorrowStatus status,
                              @Param("startBorrowDate")LocalDateTime startBorrowDate,
                              @Param("endBorrowDate") LocalDateTime endBorrowDate,
                              @Param("startReturnDate") LocalDateTime startReturnDate,
                              @Param("endReturnDate") LocalDateTime endReturnDate,
                              Pageable pageable);
}
