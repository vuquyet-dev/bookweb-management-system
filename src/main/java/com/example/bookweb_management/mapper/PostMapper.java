package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.postdto.PostCreateDTO;
import com.example.bookweb_management.dto.postdto.PostResponseDTO;
import com.example.bookweb_management.dto.postdto.PostUpdateDTO;
import com.example.bookweb_management.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    // CreateDTO -> Entity
    Post toEntity(PostCreateDTO createDTO);

    // UpdateDTO -> Entity
    Post toEntity(PostUpdateDTO updateDTO);

    // Entity -> ResponseDTO
    @Mapping(source = "user.id", target = "userId")
    PostResponseDTO toResponseDTO(Post entity);

    // List<Entity> -> List<ResponseDTO>
    @Mapping(source = "user.id", target = "userId")//mapping id của user để khi test api get không bị null userId
    List<PostResponseDTO> toResponseDTOs(List<Post> entities);
}
