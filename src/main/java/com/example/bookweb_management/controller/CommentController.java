package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.commentdto.CommentCreateDTO;
import com.example.bookweb_management.dto.commentdto.CommentResponseDTO;
import com.example.bookweb_management.dto.commentdto.CommentUpdateDTO;
import com.example.bookweb_management.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CommentResponseDTO> getAllComments()
    {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public CommentResponseDTO getComment(@PathVariable Long id)
    {
        return commentService.getComment(id);
    }

    @PostMapping
    public CommentResponseDTO createComment(@RequestBody CommentCreateDTO createDTO)
    {
        return commentService.createComment(createDTO);
    }

    @PutMapping("/{id}")
    public CommentResponseDTO updateComment(@PathVariable Long id,@RequestBody CommentUpdateDTO updateDTO)
    {
        return commentService.updateComment(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id)
    {
        commentService.deleteComment(id);
    }

    @GetMapping("/search")
    public Page<CommentResponseDTO> search(String keyword, int page, int size)
    {
        return commentService.search(keyword, page, size);
    }
}
