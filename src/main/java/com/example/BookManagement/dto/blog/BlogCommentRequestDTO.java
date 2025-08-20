package com.example.BookManagement.dto.blog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogCommentRequestDTO {

    @NotNull(message = "Blog ID is required")
    private Integer blogId;

    private Integer parentCommentId; // Optional, for reply

    @NotBlank(message = "Comment content is required")
    private String content;

    private String image; // Optional image URL
}
