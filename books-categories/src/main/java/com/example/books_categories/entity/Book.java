package com.example.books_categories.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @NotBlank(message = "Author(s) is required")
    @Size(max = 255, message = "Author name is too long")
    private String authors;

    @NotBlank(message = "Publisher is required")
    @Size(max = 255, message = "Publisher name is too long")
    private String publisher;

    @Min(value = 1, message = "Page count must be at least 1")
    private int pageCount;

    @NotBlank(message = "Print type is required")
    private String printType;

    @NotBlank(message = "Language is required")
    private String language;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
}
