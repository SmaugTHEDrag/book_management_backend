package com.example.BookManagement.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
 * Data Transfer Object (DTO) for Book Entity
 * Used to send book information between backend and client
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    // Book ID
    private Integer id;

    // Title of the book
    private String title;

    // Author of the book
    private String author;

    // Category or genre of the book
    private String category;

    // URL of the book cover image
    private String image;

    // Description of the book, e.g., summary or review
    private String description;

    // URL of the book's PDF file (if available)
    private String pdf;

    // Book creation timestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // Book last update timestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
