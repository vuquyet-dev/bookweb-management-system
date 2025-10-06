package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordCreateDTO;
import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordResponseDTO;
import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordUpdateDTO;
import com.example.bookweb_management.entity.Book;
import com.example.bookweb_management.entity.BorrowRecord;
import com.example.bookweb_management.entity.User;
import com.example.bookweb_management.enums.BorrowStatus;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.mapper.BorrowRecordMapper;
import com.example.bookweb_management.repository.BookRepository;
import com.example.bookweb_management.repository.BorrowRecordRepository;
import com.example.bookweb_management.repository.UserRepository;
import com.example.bookweb_management.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Override
    public BorrowRecordResponseDTO borrowBook(BorrowRecordCreateDTO createDTO) {
        User user = userRepository.findById(createDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + createDTO.getUserId()));

        Book book = bookRepository.findById(createDTO.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + createDTO.getBookId()));

        if(createDTO.getDueDate().isBefore(LocalDateTime.now()))
        {
            throw new RuntimeException("The due date cannot be before the borrow date!");
        }

        BorrowRecord convert = borrowRecordMapper.toEntity(createDTO);
        // map từ Long -> Entity vì Mapper không tự map được
        convert.setUser(user);
        convert.setBook(book);
        BorrowRecord savedRecord = borrowRecordRepository.save(convert);

        return borrowRecordMapper.toResponseDTO(savedRecord);
    }

    @Override
    public BorrowRecordResponseDTO updateBorrow(Long id, BorrowRecordUpdateDTO updateDTO) {
        BorrowRecord borrowRecord = borrowRecordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Borrow record not found with id: " + id));

        User user = userRepository.findById(updateDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + updateDTO.getUserId()));

        Book book = bookRepository.findById(updateDTO.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + updateDTO.getBookId()));

        if(updateDTO.getDueDate().isBefore(LocalDateTime.now()))
        {
            throw new RuntimeException("The due date cannot be before the borrow date!");
        }

        borrowRecord.setUser(user);
        borrowRecord.setBook(book);
        borrowRecord.setDueDate(updateDTO.getDueDate());

        BorrowRecord savedRecord = borrowRecordRepository.save(borrowRecord);

        return borrowRecordMapper.toResponseDTO(savedRecord);
    }

    @Override
    public BorrowRecordResponseDTO returnBook(Long id) {
        BorrowRecord borrowRecord = borrowRecordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Borrow record not found with id: " + id));

        if(borrowRecord.getReturnDate() != null)
        {
            throw new RuntimeException("This book has already been returned");
        }

        borrowRecord.setReturnDate(LocalDateTime.now());

        if(borrowRecord.getReturnDate().isBefore(borrowRecord.getDueDate()))
        {
            borrowRecord.setStatus(BorrowStatus.RETURNED);
        }else{
            borrowRecord.setStatus(BorrowStatus.OVERDUE);
        }

        BorrowRecord savedRecord = borrowRecordRepository.save(borrowRecord);

        return borrowRecordMapper.toResponseDTO(savedRecord);
    }

    @Override
    public List<BorrowRecordResponseDTO> getAlls() {
        return borrowRecordMapper.toResponseDTOs(borrowRecordRepository.findAll());
    }

    @Override
    public void deleteBorrow(Long id) {
        BorrowRecord borrowRecord = borrowRecordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Borrow record not found with id: " + id));
        borrowRecordRepository.deleteById(id);
    }

    @Override
    public Page<BorrowRecordResponseDTO> search(Long userId,
                                                Long bookId,
                                                BorrowStatus status,
                                                LocalDateTime startBorrowDate,
                                                LocalDateTime endBorrowDate,
                                                LocalDateTime startReturnDate,
                                                LocalDateTime endReturnDate,
                                                int page,
                                                int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<BorrowRecord> borrowRecords =
                borrowRecordRepository
                        .search(userId, bookId, status, startBorrowDate, endBorrowDate, startReturnDate, endReturnDate, pageable);

        return borrowRecords.map(borrowRecordMapper::toResponseDTO);
    }
}
