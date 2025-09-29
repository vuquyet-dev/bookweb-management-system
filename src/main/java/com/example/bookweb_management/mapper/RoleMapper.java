package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.roledto.RoleCreateDTO;
import com.example.bookweb_management.dto.roledto.RoleResponseDTO;
import com.example.bookweb_management.dto.roledto.RoleUpdateDTO;
import com.example.bookweb_management.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    // Create DTO -> Entity
    Role toEntity(RoleCreateDTO createDTO);

    // Update DTO -> Entity
    Role toEntity(RoleUpdateDTO updateDTO);

    // Entity -> Response DTO
    RoleResponseDTO toResponseDTO(Role entity);

    // List<Entity> -> List<ResponseDTO)
    List<RoleResponseDTO> toResponseDTOs(List<Role> entities);

}
