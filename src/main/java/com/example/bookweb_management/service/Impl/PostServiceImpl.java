package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.postdto.PostCreateDTO;
import com.example.bookweb_management.dto.postdto.PostResponseDTO;
import com.example.bookweb_management.dto.postdto.PostUpdateDTO;
import com.example.bookweb_management.entity.Post;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.mapper.PostMapper;
import com.example.bookweb_management.repository.PostRepository;
import com.example.bookweb_management.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<PostResponseDTO> getAllPosts() {
        return postMapper.toResponseDTOs(postRepository.findAll());
    }

    @Override
    public PostResponseDTO getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + id));
        return postMapper.toResponseDTO(post);
    }

    @Override
    public PostResponseDTO createPost(PostCreateDTO createDTO) {
        Post post = postMapper.toEntity(createDTO);
        Post savedPost = postRepository.save(post);
        return postMapper.toResponseDTO(savedPost);
    }

    @Override
    public PostResponseDTO updatePost(Long id, PostUpdateDTO updateDTO) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + id));
        Post convert = postMapper.toEntity(updateDTO);
        post.setTitle(convert.getTitle());
        post.setContent(convert.getContent());
        Post savedPost = postRepository.save(post);
        return postMapper.toResponseDTO(savedPost);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Page<PostResponseDTO> search(String keyword, int page, int size) {
        return null;
    }
}
