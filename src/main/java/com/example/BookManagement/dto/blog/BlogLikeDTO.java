package com.example.BookManagement.dto.blog;

import lombok.Data;

import java.time.LocalDateTime;

/*
 * Data Transfer Object (DTO) for BlogLike entity
 * Used to transfer information about a user's like on a blog from backend to client
 */
@Data
public class BlogLikeDTO {

    // Blog Like ID
    private Integer id;

    // ID of the blog that is liked
    private Integer blogId;

    // Username of the user who like the blog
    private String username;

    // Timestamp when the blog is liked
    private LocalDateTime likedAt;
}
