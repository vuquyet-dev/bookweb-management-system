package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.commentdto.CommentCreateDTO;
import com.example.bookweb_management.dto.commentdto.CommentResponseDTO;
import com.example.bookweb_management.dto.commentdto.CommentUpdateDTO;
import com.example.bookweb_management.entity.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    //CreateDTO to Entity
    Comment toEntity(CommentCreateDTO createDTO);

    //UpdateDTO to Entity
    Comment toEntity(CommentUpdateDTO updateDTO);

    //Entity to ResponseDTO
    CommentResponseDTO toResponseDTO(Comment entity);

    //List Entity to ResponseDTO
    List<CommentResponseDTO> toResponseDTOs(List<Comment> entities);
}
