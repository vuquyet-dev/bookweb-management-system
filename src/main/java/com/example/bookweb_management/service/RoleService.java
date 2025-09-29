package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.roledto.RoleCreateDTO;
import com.example.bookweb_management.dto.roledto.RoleResponseDTO;
import com.example.bookweb_management.dto.roledto.RoleUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    public List<RoleResponseDTO> getAllRoles();
    public RoleResponseDTO getRole(Long id);
    public RoleResponseDTO createRole(RoleCreateDTO createDTO);
    public RoleResponseDTO updateRole(Long id, RoleUpdateDTO updateDTO);
    public void deleteRole(Long id);
    public Page<RoleResponseDTO> search(String keyword, int page, int size);

    RoleResponseDTO addPermission(Long roleId, Long permissionId);
    RoleResponseDTO removePermission(Long roleId, Long permissionId);

}
