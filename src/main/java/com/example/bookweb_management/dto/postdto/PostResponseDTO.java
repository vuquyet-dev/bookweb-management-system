package com.example.bookweb_management.dto.postdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
