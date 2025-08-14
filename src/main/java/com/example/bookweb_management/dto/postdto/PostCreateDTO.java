package com.example.bookweb_management.dto.postdto;

import com.example.bookweb_management.constant.PostConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO {

    @NotBlank(message = "Title " + PostConstant.MSG_NOTBLANK)
    @Size(min = 1, max = 200, message = PostConstant.MSG_TITLE_SIZE)
    private String title;

    @NotBlank(message = "Content " + PostConstant.MSG_NOTBLANK)
    @Size(min = 1, max = 10000, message = PostConstant.MSG_CONTENT_SIZE)
    private String content;
}
