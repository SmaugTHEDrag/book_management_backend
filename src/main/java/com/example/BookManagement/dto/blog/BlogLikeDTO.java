package com.example.BookManagement.dto.blog;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogLikeDTO {

    private Integer id;             // Like ID
    private Integer blogId;         // Associated Blog ID
    private String username;        // User who like a blog
    private LocalDateTime likedAt;  // Timestamp when like
}
