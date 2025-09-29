package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.permission.PermissionCreateDTO;
import com.example.bookweb_management.dto.permission.PermissionResponseDTO;
import com.example.bookweb_management.dto.permission.PermissionUpdateDTO;
import com.example.bookweb_management.entity.Permission;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.exception.category_exception.DuplicateNameException;
import com.example.bookweb_management.mapper.PermissionMapper;
import com.example.bookweb_management.repository.PermissionRepository;
import com.example.bookweb_management.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public PermissionResponseDTO getPermission(Long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found permission with id: " + id));
        return permissionMapper.toResponseDTO(permission);
    }

    @Override
    public List<PermissionResponseDTO> getAllPermissions() {
        return permissionMapper.toResponseDTOs(permissionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))); // sort by id
    }

    @Override
    public PermissionResponseDTO createPermission(PermissionCreateDTO createDTO) {
        if(permissionRepository.existsByName(createDTO.getName()))
        {
            throw new DuplicateNameException("Permission " + createDTO.getName() + " already exists");
        }
        Permission permission = permissionMapper.toEntity(createDTO);
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toResponseDTO(savedPermission);
    }

    @Override
    public PermissionResponseDTO updatePermission(Long id, PermissionUpdateDTO updateDTO) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found permission with id: " + id));
        permission.setName(updateDTO.getName());
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toResponseDTO(savedPermission);
    }

    @Override
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public Page<PermissionResponseDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Permission> permissions = permissionRepository.searchByName(keyword, pageable);

        return permissions.map(permission -> {
            PermissionResponseDTO dto = new PermissionResponseDTO();
            dto.setId(permission.getId());
            dto.setName(permission.getName());
            return dto;
        });
    }
}
