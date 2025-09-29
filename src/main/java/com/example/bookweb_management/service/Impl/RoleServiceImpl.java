package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.roledto.RoleCreateDTO;
import com.example.bookweb_management.dto.roledto.RoleResponseDTO;
import com.example.bookweb_management.dto.roledto.RoleUpdateDTO;
import com.example.bookweb_management.entity.Permission;
import com.example.bookweb_management.entity.Role;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.mapper.RoleMapper;
import com.example.bookweb_management.repository.PermissionRepository;
import com.example.bookweb_management.repository.RoleRepository;
import com.example.bookweb_management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        return roleMapper.toResponseDTOs(roleRepository.findAll());
    }

    @Override
    public RoleResponseDTO getRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found role with id: " + id));
        return roleMapper.toResponseDTO(role);
    }

    @Override
    public RoleResponseDTO createRole(RoleCreateDTO createDTO) {
        Role role = roleMapper.toEntity(createDTO);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toResponseDTO(savedRole);
    }

    @Override
    public RoleResponseDTO updateRole(Long id, RoleUpdateDTO updateDTO) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found role with id: " + id));
        Role convert = roleMapper.toEntity(updateDTO);
        role.setName(convert.getName());
        Role savedRole = roleRepository.save(role);
        return roleMapper.toResponseDTO(savedRole);
    }

    @Override
    public void deleteRole(Long id) {
        if(!roleRepository.existsById(id))
        {
            throw new RuntimeException("Role not found");
        }
        roleRepository.deleteById(id);
    }

    @Override
    public Page<RoleResponseDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> roles = roleRepository.searchByName(keyword, pageable);

        return roles.map(role -> {
            RoleResponseDTO dto = new RoleResponseDTO();
            dto.setId(role.getId());
            dto.setName(role.getName());
            return dto;
        });
    }

    @Override
    public RoleResponseDTO assignPermissions(Long id, List<Long> permissionIds) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        role.setPermissions(new HashSet<>(permissions));

        Role saved = roleRepository.save(role);
        return roleMapper.toResponseDTO(saved);
    }
}
