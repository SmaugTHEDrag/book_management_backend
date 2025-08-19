package com.example.BookManagement.dto.book;

import lombok.Data;

@Data
public class BookRequestDTO {
    private String title;
    private String author;
    private String category;
    private String image;
    private String description;
    private String pdf;
}
