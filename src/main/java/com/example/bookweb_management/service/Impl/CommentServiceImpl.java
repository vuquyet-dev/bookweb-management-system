package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.commentdto.CommentCreateDTO;
import com.example.bookweb_management.dto.commentdto.CommentResponseDTO;
import com.example.bookweb_management.dto.commentdto.CommentUpdateDTO;
import com.example.bookweb_management.entity.Comment;
import com.example.bookweb_management.entity.Post;
import com.example.bookweb_management.entity.User;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.mapper.CommentMapper;
import com.example.bookweb_management.repository.CommentRepository;
import com.example.bookweb_management.repository.PostRepository;
import com.example.bookweb_management.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

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
        User user = userRepository.findById(createDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + createDTO.getUserId()));
        Post post = postRepository.findById(createDTO.getPostId()).orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + createDTO.getPostId()));
        Comment comment = commentMapper.toEntity(createDTO);
        comment.setUser(user);
        comment.setPost(post);
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
