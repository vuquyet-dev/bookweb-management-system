package com.example.bookweb_management.dto.borrowrecorddto;

import com.example.bookweb_management.dto.bookdto.BookResponseDTO;
import com.example.bookweb_management.dto.userdto.UserResponseDTO;
import com.example.bookweb_management.entity.Book;
import com.example.bookweb_management.entity.User;
import com.example.bookweb_management.enums.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecordResponseDTO {
    private Long id;
    private UserResponse user; // mapping các thuộc tính của User sang UserResponseDTO
    private BookResponseDTO book;
    private LocalDateTime borrowDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private BorrowStatus status;
}
