package com.example.bookweb_management.dto.roledto;

import com.example.bookweb_management.constant.RoleConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateDTO {
    @NotBlank(message = "Name " + RoleConstants.MSG_NOTBLANK)
    @Size(min = 1, max = 255, message = RoleConstants.MSG_NAME_SIZE)
    private String name;
}
