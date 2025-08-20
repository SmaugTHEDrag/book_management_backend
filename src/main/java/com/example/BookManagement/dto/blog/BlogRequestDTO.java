package com.example.BookManagement.dto.blog;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BlogRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private String image; // Optional image URL
}
