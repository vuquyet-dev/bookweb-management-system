package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.roledto.RoleCreateDTO;
import com.example.bookweb_management.dto.roledto.RoleResponseDTO;
import com.example.bookweb_management.dto.roledto.RoleUpdateDTO;
import com.example.bookweb_management.entity.Permission;
import com.example.bookweb_management.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    // Create DTO -> Entity
    Role toEntity(RoleCreateDTO createDTO);

    // Update DTO -> Entity
    Role toEntity(RoleUpdateDTO updateDTO);

    // Entity -> Response DTO
    @Mapping(target = "permissionName", expression = "java(mapPermissions(entity))") // sort permission tăng dần
    RoleResponseDTO toResponseDTO(Role entity);

    // List<Entity> -> List<ResponseDTO)
    List<RoleResponseDTO> toResponseDTOs(List<Role> entities);

    default Set<String> mapPermissions(Role role)
    {
        return role.getPermissions().stream()
                .sorted(Comparator.comparing(Permission::getId))
                .map(Permission::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
