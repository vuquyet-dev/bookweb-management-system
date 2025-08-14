package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.postdto.PostCreateDTO;
import com.example.bookweb_management.dto.postdto.PostResponseDTO;
import com.example.bookweb_management.dto.postdto.PostUpdateDTO;
import com.example.bookweb_management.entity.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    //CreateDTO to Entity
    Post toEntity(PostCreateDTO createDTO);

    //UpdateDTO to Entity
    Post toEntity(PostUpdateDTO updateDTO);

    //Entity to ResponseDTO
    PostResponseDTO toResponseDTO(Post entity);

    //List Entity to ResponseDTO
    List<PostResponseDTO> toResponseDTOs(List<Post> entities);
}
