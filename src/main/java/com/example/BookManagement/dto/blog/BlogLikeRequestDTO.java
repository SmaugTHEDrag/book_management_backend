package com.example.BookManagement.dto.blog;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogLikeRequestDTO {
    @NotNull(message = "Blog ID is required")
    private Integer blogId;
}
