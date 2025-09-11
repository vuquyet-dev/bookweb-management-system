package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.commentdto.CommentCreateDTO;
import com.example.bookweb_management.dto.commentdto.CommentResponseDTO;
import com.example.bookweb_management.dto.commentdto.CommentUpdateDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface CommentService {
    public List<CommentResponseDTO> getAllComments();
    public CommentResponseDTO getComment(Long id);
    public CommentResponseDTO createComment(CommentCreateDTO createDTO);
    public CommentResponseDTO updateComment(Long id, CommentUpdateDTO updateDTO);
    public void deleteComment(Long id);
    public Page<CommentResponseDTO> search(String keyword, int page, int size);

    public void commentsExcelExport(HttpServletResponse httpServletResponse) throws IOException;
}
