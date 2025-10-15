package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.commentdto.CommentCreateDTO;
import com.example.bookweb_management.dto.commentdto.CommentResponseDTO;
import com.example.bookweb_management.dto.commentdto.CommentUpdateDTO;
import com.example.bookweb_management.service.CommentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/all")
    public List<CommentResponseDTO> getAllComments()
    {
        return commentService.getAllComments();
    }

    @GetMapping("/get/{id}")
    public CommentResponseDTO getComment(@PathVariable Long id)
    {
        return commentService.getComment(id);
    }

    @PostMapping("/create")
    public CommentResponseDTO createComment(@RequestBody CommentCreateDTO createDTO)
    {
        return commentService.createComment(createDTO);
    }

    @PutMapping("/update/{id}")
    public CommentResponseDTO updateComment(@PathVariable Long id,@RequestBody CommentUpdateDTO updateDTO)
    {
        return commentService.updateComment(id, updateDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id) throws AccessDeniedException {
        commentService.deleteComment(id);
    }

    @GetMapping("/search")
    public Page<CommentResponseDTO> search(@RequestParam(defaultValue = "") String keyword,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size)
    {
        return commentService.search(keyword, page, size);
    }

    @GetMapping("/excel")
    public void commentExcelExport(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=comments-list.xlsx";

        httpServletResponse.setHeader(headerKey, headerValue);

        commentService.commentsExcelExport(httpServletResponse);
    }
}
