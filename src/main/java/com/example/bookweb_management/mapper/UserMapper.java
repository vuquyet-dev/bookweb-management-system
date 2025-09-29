package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.userdto.UserCreateDTO;
import com.example.bookweb_management.dto.userdto.UserResponseDTO;
import com.example.bookweb_management.dto.userdto.UserUpdateDTO;
import com.example.bookweb_management.entity.Permission;
import com.example.bookweb_management.entity.Role;
import com.example.bookweb_management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // CreateDTO -> Entity
    //@Mapping(target = "username", ignore = true)
    User toEntity(UserCreateDTO dto);

    // UpdateDTO -> Entity
    //@Mapping(target = "username", ignore = true)
    User toEntity(UserUpdateDTO dto);

    // Entity -> ResponseDTO
    //@Mapping(target = "roleIds", expression = "java(entity.getRoles().stream().map(r -> r.getId()).collect(java.util.stream.Collectors.toSet()))")
    //@Mapping(target = "roleIds", source = "roles")
    UserResponseDTO toResponseDTO(User entity);

    List<UserResponseDTO> toResponseDTOs(List<User> entities);
}
