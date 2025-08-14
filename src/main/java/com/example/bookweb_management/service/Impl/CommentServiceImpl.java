package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.commentdto.CommentCreateDTO;
import com.example.bookweb_management.dto.commentdto.CommentResponseDTO;
import com.example.bookweb_management.dto.commentdto.CommentUpdateDTO;
import com.example.bookweb_management.entity.Comment;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.mapper.CommentMapper;
import com.example.bookweb_management.repository.CommentRepository;
import com.example.bookweb_management.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<CommentResponseDTO> getAllComments() {
        return commentMapper.toResponseDTOs(commentRepository.findAll());
    }

    @Override
    public CommentResponseDTO getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found comment with id: " + id));
        return commentMapper.toResponseDTO(comment);
    }

    @Override
    public CommentResponseDTO createComment(CommentCreateDTO createDTO) {
        Comment comment = commentMapper.toEntity(createDTO);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toResponseDTO(savedComment);
    }

    @Override
    public CommentResponseDTO updateComment(Long id, CommentUpdateDTO updateDTO) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found comment with id: " + id));
        Comment convert = commentMapper.toEntity(updateDTO);
        comment.setContent(convert.getContent());
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toResponseDTO(savedComment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Page<CommentResponseDTO> search(String keyword, int page, int size) {
        return null;
    }
}
