package com.example.BookManagement.dto.blog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/*
 * Data Transfer Object (DTO) for creating a comment on a blog
 * Receives input from client (request body) when a user posts a comment or a reply
 * Validation ensures input data is correct before service processing
 */
@Data
public class BlogCommentRequestDTO {

    // ID of the blog to comment on
    @NotNull(message = "Blog ID is required")
    private Integer blogId;

    // Optional ID of the parent comment if this is a reply
    private Integer parentCommentId;

    // Content of the comment
    @NotBlank(message = "Comment content is required")
    private String content;

    // Optional image URL associated with the comment
    private String image;
}
