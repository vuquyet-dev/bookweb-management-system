package com.example.bookweb_management.entity;

import com.example.bookweb_management.enums.BorrowStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "borrow_records")
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // n BorrowRecord - 1 User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private User user;

    // n BorrowRecord - 1 Book
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "Book cannot be null")
    private Book book;

    @Column(name = "borrow_date", nullable = false, updatable = false)
    @NotNull(message = "Borrow date cannot be null")
    private LocalDateTime borrowDate; // ngày mượn

    @Column(name = "due_date", nullable = false)
    @NotNull(message = "Due date cannot be null")
    private LocalDateTime dueDate; // ngày phải trả

    @Column(name = "return_date", nullable = true)
    private LocalDateTime returnDate; //ngày trả thực tế

    @Enumerated(EnumType.STRING)
    private BorrowStatus status = BorrowStatus.BORROWED;

    @PrePersist
    public void borrowTime()
    {
        this.borrowDate = LocalDateTime.now();
        this.status = BorrowStatus.BORROWED;
    }

    @PreUpdate
    public void returnTime()
    {
        this.returnDate = LocalDateTime.now();
    }
}
