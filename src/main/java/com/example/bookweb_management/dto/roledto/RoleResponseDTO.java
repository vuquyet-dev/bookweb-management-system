package com.example.bookweb_management.dto.roledto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO {
    private Long id;
    private String name;
    private Set<String> permissionName;
}
