package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.permission.PermissionCreateDTO;
import com.example.bookweb_management.dto.permission.PermissionResponseDTO;
import com.example.bookweb_management.dto.permission.PermissionUpdateDTO;
import com.example.bookweb_management.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/get/{id}")
    public PermissionResponseDTO getPermission(@PathVariable Long id)
    {
        return permissionService.getPermission(id);
    }

    @GetMapping("/all")
    public List<PermissionResponseDTO> permissionResponseDTOS()
    {
        return permissionService.getAllPermissions();
    }

    @PostMapping("/create")
    public PermissionResponseDTO createPermission(@RequestBody @Valid PermissionCreateDTO createDTO)
    {
        return permissionService.createPermission(createDTO);
    }

    @PutMapping("/update/{id}")
    public PermissionResponseDTO updatePermission(@PathVariable Long id, @RequestBody @Valid PermissionUpdateDTO updateDTO)
    {
        return permissionService.updatePermission(id, updateDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePermission(@PathVariable Long id)
    {
        permissionService.deletePermission(id);
    }

    @GetMapping("/search")
    public Page<PermissionResponseDTO> search(@RequestParam(defaultValue = "") String keyword,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size)
    {
        return permissionService.search(keyword, page, size);
    }
}
