package com.example.BookManagement.dto.blog;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlogDTO {
    private Integer id;                   // Blog ID
    private String title;                 // Blog Title
    private String content;               // Blog content
    private String image;                 // Optional image URL
    private String username;              // User who post a blog
    private Integer userId;
    private LocalDateTime createdAt;      // Creation timestamp
    private LocalDateTime updatedAt;      // Last update timestamp

    private Long likeCount;             // Total likes
    private List<BlogCommentDTO> comments; // Nested comments
}
