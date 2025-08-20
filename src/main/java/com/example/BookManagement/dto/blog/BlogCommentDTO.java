package com.example.BookManagement.dto.blog;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlogCommentDTO {

    private Integer id;                 // Blog comment ID
    private Integer blogId;             // Blog ID
    private String username;            // User comment
    private String content;             // Comment content
    private String image;               // Optional imageURL
    private LocalDateTime createdAt;    // Creation timestamp
    private LocalDateTime updatedAt;    // Last update timestamp

    private List<BlogCommentDTO> replies; // Nested replies
}
