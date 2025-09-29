package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.permission.PermissionCreateDTO;
import com.example.bookweb_management.dto.permission.PermissionResponseDTO;
import com.example.bookweb_management.dto.permission.PermissionUpdateDTO;
import com.example.bookweb_management.entity.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    // CreateDTO -> Entity
    Permission toEntity(PermissionCreateDTO createDTO);

    // UpdateDTO -> Entity
    Permission toEntity(PermissionUpdateDTO updateDTO);

    // List<Entity> -> List<ResponseDTO>
    List<PermissionResponseDTO> toResponseDTOs(List<Permission> entities);

    // Entity -> ResponseDTO
    PermissionResponseDTO toResponseDTO(Permission entity);
}
