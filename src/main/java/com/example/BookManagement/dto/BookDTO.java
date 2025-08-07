package com.example.BookManagement.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Integer id;
    private String title;
    private String author;
    private String category;
    private String image;
    private String description;
    private String pdf;
    private String createdAt;
    private String updatedAt;
}
