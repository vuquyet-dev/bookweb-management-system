package com.example.bookweb_management.dto.postdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOfCommentResponse {
    private Long id;
    private String fullname;
}
