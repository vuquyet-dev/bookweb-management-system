package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.postdto.PostCreateDTO;
import com.example.bookweb_management.dto.postdto.PostResponseDTO;
import com.example.bookweb_management.dto.postdto.PostUpdateDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

public interface PostService {
    public List<PostResponseDTO> getAllPosts();
    public PostResponseDTO getPost(Long id);
    public PostResponseDTO createPost(PostCreateDTO createDTO);
    public PostResponseDTO updatePost(Long id, PostUpdateDTO updateDTO);
    public void deletePost(Long id) throws AccessDeniedException;
    public Page<PostResponseDTO> search(String keyword, int page, int size);
    public void postsExcelExport(HttpServletResponse httpServletResponse) throws IOException;
}
