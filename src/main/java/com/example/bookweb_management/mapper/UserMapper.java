package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.UserCreateDTO;
import com.example.bookweb_management.dto.UserResponseDTO;
import com.example.bookweb_management.dto.UserUpdateDTO;
import com.example.bookweb_management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // CreateDTO -> Entity
    //@Mapping(target = "username", ignore = true)
    User toEntity(UserCreateDTO dto);

    // UpdateDTO -> Entity
    //@Mapping(target = "username", ignore = true)
    User toEntity(UserUpdateDTO dto);

    // Entity -> ResponseDTO
    UserResponseDTO toResponseDTO(User entity);

    List<UserResponseDTO> toResponseDTOs(List<User> entities);
}
