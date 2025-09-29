package com.example.bookweb_management.dto.permission;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCreateDTO {

    @NotBlank(message = "Name must me not blank")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;
}
