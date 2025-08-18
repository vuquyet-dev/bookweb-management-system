package com.example.bookweb_management.dto.commentdto;

import com.example.bookweb_management.constant.CommentConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDTO {
    @NotBlank(message = CommentConstant.MSG_CONTENT_NOTBLANK)
    @Size(min = 1, max = 1000, message = CommentConstant.MSG_CONTENT_SIZE)
    private String content;

    @NotNull(message = "User id is not null")
    private Long userId;

    @NotNull(message = "User id is not null")
    private Long postId;
}
