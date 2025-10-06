package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordCreateDTO;
import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordResponseDTO;
import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordUpdateDTO;
import com.example.bookweb_management.enums.BorrowStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface BorrowRecordService {
    public BorrowRecordResponseDTO borrowBook(BorrowRecordCreateDTO createDTO);
    public BorrowRecordResponseDTO updateBorrow(Long id, BorrowRecordUpdateDTO updateDTO);
    public BorrowRecordResponseDTO returnBook(Long id);
    public List<BorrowRecordResponseDTO> getAlls();
    public void deleteBorrow(Long id);

    public Page<BorrowRecordResponseDTO> search(Long userId,
                                                Long bookId,
                                                BorrowStatus status,
                                                LocalDateTime startBorrowDate,
                                                LocalDateTime endBorrowDate,
                                                LocalDateTime startReturnDate,
                                                LocalDateTime endReturnDate,
                                                int page,
                                                int size);

}
