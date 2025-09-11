package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.postdto.PostCreateDTO;
import com.example.bookweb_management.dto.postdto.PostResponseDTO;
import com.example.bookweb_management.dto.postdto.PostUpdateDTO;
import com.example.bookweb_management.entity.Post;
import com.example.bookweb_management.entity.User;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.mapper.PostMapper;
import com.example.bookweb_management.repository.PostRepository;
import com.example.bookweb_management.repository.UserRepository;
import com.example.bookweb_management.service.PostService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserRepository userRepository;//mapping user id to create post

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
        User user = userRepository.findById(createDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + createDTO.getUserId()));
        Post post = postMapper.toEntity(createDTO);
        post.setUser(user);
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

    @Override
    public void postsExcelExport(HttpServletResponse httpServletResponse) throws IOException {
        List<Post> posts = postRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Posts Info");

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Title");
        row.createCell(2).setCellValue("Content");
        row.createCell(3).setCellValue("Create At");
        row.createCell(4).setCellValue("Update At");
        row.createCell(5).setCellValue("User ID");

        int dataRowIndex = 1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for(Post post : posts)
        {
            XSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(post.getId());
            dataRow.createCell(1).setCellValue(post.getTitle());
            dataRow.createCell(2).setCellValue(post.getContent());
            dataRow.createCell(3).setCellValue(post.getCreateAt().format(formatter));
            dataRow.createCell(4).setCellValue(post.getUpdateAt().format(formatter));
            dataRow.createCell(5).setCellValue(post.getUser().getId());
            dataRowIndex++;
        }

        for(int i = 0; i < 6; i++)
        {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
        }

        ServletOutputStream sos = httpServletResponse.getOutputStream();
        workbook.write(sos);
        workbook.close();
        sos.close();
    }
}
