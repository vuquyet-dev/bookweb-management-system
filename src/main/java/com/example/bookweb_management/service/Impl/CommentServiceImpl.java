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
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.searchByContent(keyword, pageable);

        return comments.map(comment -> {
            CommentResponseDTO dto = new CommentResponseDTO();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dto.setCreateAt(comment.getCreateAt());
            dto.setUpdateAt(comment.getUpdateAt());
            dto.setUserId(comment.getUser().getId());
            dto.setPostId(comment.getPost().getId());
            return dto;
        });
    }

    @Override
    public void commentsExcelExport(HttpServletResponse httpServletResponse) throws IOException {
        List<Comment> comments = commentRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Comments Info");

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Content");
        row.createCell(2).setCellValue("Create At");
        row.createCell(3).setCellValue("Update At");
        row.createCell(4).setCellValue("User ID");
        row.createCell(5).setCellValue("Post ID");

        int dataRowIndex = 1;

        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy HH:mm:ss"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for(Comment comment : comments)
        {
            XSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(comment.getId());
            dataRow.createCell(1).setCellValue(comment.getContent());
            dataRow.createCell(2).setCellValue(comment.getCreateAt().format(formatter));
            dataRow.createCell(3).setCellValue(comment.getUpdateAt().format(formatter));
            dataRow.createCell(4).setCellValue(comment.getUser().getId());
            dataRow.createCell(5).setCellValue(comment.getPost().getId());
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
