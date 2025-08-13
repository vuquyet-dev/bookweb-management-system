package com.example.bookweb_management.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(name = "page_count", nullable = false)
    private int pageCount;

    @Column(name = "print_type", nullable = false)
    private String printType;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String description;
}
