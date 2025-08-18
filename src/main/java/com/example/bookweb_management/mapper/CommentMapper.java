package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.commentdto.CommentCreateDTO;
import com.example.bookweb_management.dto.commentdto.CommentResponseDTO;
import com.example.bookweb_management.dto.commentdto.CommentUpdateDTO;
import com.example.bookweb_management.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    //CreateDTO to Entity
    Comment toEntity(CommentCreateDTO createDTO);

    //UpdateDTO to Entity
    Comment toEntity(CommentUpdateDTO updateDTO);

    //Entity to ResponseDTO
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "post.id", target = "postId")
    CommentResponseDTO toResponseDTO(Comment entity);

    //List Entity to ResponseDTO
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "post.id", target = "postId")
    List<CommentResponseDTO> toResponseDTOs(List<Comment> entities);
}
