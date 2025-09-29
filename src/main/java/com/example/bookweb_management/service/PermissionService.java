package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.permission.PermissionCreateDTO;
import com.example.bookweb_management.dto.permission.PermissionResponseDTO;
import com.example.bookweb_management.dto.permission.PermissionUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PermissionService {
    public PermissionResponseDTO getPermission(Long id);
    public List<PermissionResponseDTO> getAllPermissions();
    public PermissionResponseDTO createPermission(PermissionCreateDTO createDTO);
    public PermissionResponseDTO updatePermission(Long id, PermissionUpdateDTO updateDTO);
    public void deletePermission(Long id);
    Page<PermissionResponseDTO> search(String keyword, int page, int size);
}
