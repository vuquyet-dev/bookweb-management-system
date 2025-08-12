package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.UserDTO;
import com.example.bookweb_management.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User entity);
    User toEntity(UserDTO dto);

    List<UserDTO> toDTOs(List<User> entities);
}
