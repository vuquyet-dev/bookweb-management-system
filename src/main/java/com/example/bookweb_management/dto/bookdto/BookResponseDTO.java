package com.example.bookweb_management.dto.bookdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private int pageCount;
    private String printType;
    private String language;
    private String description;
    private Long userId;
    private List<Long> categoryIds;
}
