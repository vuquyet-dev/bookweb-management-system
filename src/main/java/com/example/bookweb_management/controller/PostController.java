package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.postdto.PostCreateDTO;
import com.example.bookweb_management.dto.postdto.PostResponseDTO;
import com.example.bookweb_management.dto.postdto.PostUpdateDTO;
import com.example.bookweb_management.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostResponseDTO> getAllPosts()
    {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostResponseDTO getPost(@PathVariable Long id)
    {
        return postService.getPost(id);
    }

    @PostMapping
    public PostResponseDTO createPost(@RequestBody PostCreateDTO createDTO)
    {
        return postService.createPost(createDTO);
    }

    @PutMapping("/{id}")
    public PostResponseDTO updatePost(@PathVariable Long id,@RequestBody PostUpdateDTO updateDTO)
    {
        return postService.updatePost(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id)
    {
        postService.deletePost(id);
    }

    @GetMapping("/search")
    public Page<PostResponseDTO> search(String keyword, int page, int size)
    {
        return postService.search(keyword, page, size);
    }
}
