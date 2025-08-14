package com.example.bookweb_management.dto.categorydto;

import com.example.bookweb_management.constant.CategoryConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDTO {
    @NotBlank(message = CategoryConstant.MSG_NAME_NOTBLANK)
    @Size(min = 1, max = 100, message = CategoryConstant.MSG_NAME_SIZE)
    private String name;
}
