package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.roledto.RoleCreateDTO;
import com.example.bookweb_management.dto.roledto.RoleResponseDTO;
import com.example.bookweb_management.dto.roledto.RoleUpdateDTO;
import com.example.bookweb_management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/get/{id}")
    public RoleResponseDTO getRole(@PathVariable Long id)
    {
        return roleService.getRole(id);
    }

    @GetMapping("/all")
    public List<RoleResponseDTO> getAllRoles()
    {
        return roleService.getAllRoles();
    }

    @PostMapping("/create")
    public RoleResponseDTO createRole(@RequestBody RoleCreateDTO createDTO)
    {
        return roleService.createRole(createDTO);
    }

    @PutMapping("/update/{id}")
    public RoleResponseDTO updateRole(@PathVariable Long id, @RequestBody RoleUpdateDTO updateDTO)
    {
        return roleService.updateRole(id, updateDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRole(@PathVariable Long id)
    {
        roleService.deleteRole(id);
    }

    @GetMapping("/search")
    public Page<RoleResponseDTO> search(@RequestParam(defaultValue = "") String keyword,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size)
    {
        return roleService.search(keyword, page, size);
    }

    @PutMapping("/{id}/permissions")
    public ResponseEntity<RoleResponseDTO> assignPermissions(@PathVariable Long id,
                                                             @RequestBody List<Long> permissionIds)
    {
        return ResponseEntity.ok(roleService.assignPermissions(id, permissionIds));
    }
}
