package com.example.BookManagement.dto;

import lombok.Data;

@Data
public class FavoriteDTO {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private String title;
    private String author;
    private String category;
    private String image;
}
