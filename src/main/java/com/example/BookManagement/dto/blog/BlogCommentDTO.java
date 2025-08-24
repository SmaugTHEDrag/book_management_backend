package com.example.BookManagement.dto.blog;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Data Transfer Object (DTO) for Blog Comment entity
 * Used to transfer blog comment information from backend to client
 * Supports nested replies through the `replies` field
 */
@Data
public class BlogCommentDTO {

    // Blog Comment ID
    private Integer id;

    // ID of the blog that this comment belongs to
    private Integer blogId;

    // Username of the user who make the comment
    private String username;

    // Content of the comment
    private String content;

    // Optional image URL associated with the comment
    private String image;

    // Blog comment creation timestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // Blog comment last update timestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // List of nested replies to this comment
    private List<BlogCommentDTO> replies;
}
