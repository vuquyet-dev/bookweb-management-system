package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordCreateDTO;
import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordResponseDTO;
import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordUpdateDTO;
import com.example.bookweb_management.enums.BorrowStatus;
import com.example.bookweb_management.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/borrows")
public class BorrowRecordController {

    @Autowired
    private BorrowRecordService borrowRecordService;

    @PostMapping("/borrow")
    public BorrowRecordResponseDTO borrowBook(@RequestBody BorrowRecordCreateDTO createDTO)
    {
        return borrowRecordService.borrowBook(createDTO);
    }

    @PutMapping("/update/{id}")
    public BorrowRecordResponseDTO updateBorrow(@PathVariable Long id,
                                                @RequestBody BorrowRecordUpdateDTO updateDTO)
    {
        return borrowRecordService.updateBorrow(id, updateDTO);
    }

    @PutMapping("/return/{id}")
    public BorrowRecordResponseDTO returnBook(@PathVariable Long id)
    {
        return borrowRecordService.returnBook(id);
    }

    @GetMapping("/all")
    public List<BorrowRecordResponseDTO> getAlls()
    {
        return borrowRecordService.getAlls();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBorrow(@PathVariable Long id)
    {
        borrowRecordService.deleteBorrow(id);
        return ResponseEntity.ok("Delete successfully!");
    }

    @GetMapping("/search")
    public Page<BorrowRecordResponseDTO> search(@RequestParam(required = false) Long userId,
                                                @RequestParam(required = false) Long bookId,
                                                @RequestParam(required = false) BorrowStatus status,
                                                @RequestParam(required = false) LocalDateTime startBorrowDate,
                                                @RequestParam(required = false) LocalDateTime endBorrowDate,
                                                @RequestParam(required = false) LocalDateTime startReturnDate,
                                                @RequestParam(required = false) LocalDateTime endReturnDate,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size)
    {
        return borrowRecordService.search(userId, bookId, status, startBorrowDate, endBorrowDate, startReturnDate, endReturnDate, page, size);
    }
}
