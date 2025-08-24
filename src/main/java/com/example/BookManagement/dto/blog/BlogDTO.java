package com.example.BookManagement.dto.blog;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/*
 * Data Transfer Object (DTO) for Blog entity
 * Used to transfer blog information from backend to client
 */
@Data
public class BlogDTO {

    // Blog ID
    private Integer id;

    // Title of the Blog
    private String title;

    // Content of the Blog
    private String content;

    // Optional image URL associated with the Blog
    private String image;

    // Username of the user who post the Blog
    private String username;

    // ID of the user who post the Blog
    private Integer userId;

    // Blog creation timestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // Blog last update timestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // Total number of likes for this Blog
    private Long likeCount;

    // List of comments associated with this Blog (nested DTOs)
    private List<BlogCommentDTO> comments;
}
