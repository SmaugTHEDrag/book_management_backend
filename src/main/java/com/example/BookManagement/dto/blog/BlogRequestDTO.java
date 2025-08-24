package com.example.BookManagement.dto.blog;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * Data Transfer Object (DTO) for creating or updating a blog
 * Receives input from client (request body) for blog management operations
 * Validation ensures input data is correct before service processing
 */
@Data
public class BlogRequestDTO {

    // Title of the blog
    @NotBlank(message = "Title cannot be blank")
    private String title;

    // Content of the blog
    @NotBlank(message = "Content cannot be blank")
    private String content;

    // Optional image URL associated with the blog
    private String image;
}
