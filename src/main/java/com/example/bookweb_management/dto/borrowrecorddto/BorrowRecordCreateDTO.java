package com.example.bookweb_management.dto.borrowrecorddto;

import com.example.bookweb_management.constant.BorrowRecordConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecordCreateDTO {

    @NotNull(message = "User id " + BorrowRecordConstants.MSG_NOTNULL)
    private Long userId;

    @NotNull(message = "Book id " + BorrowRecordConstants.MSG_NOTNULL)
    private Long bookId;

    @NotNull(message = "Due date " + BorrowRecordConstants.MSG_NOTNULL)
    private LocalDateTime dueDate;
}
