package com.example.BookManagement.dto.blog;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogLikeDTO {

    private Integer id;
    private Integer blogId;
    private String username;
    private LocalDateTime likedAt;
}
