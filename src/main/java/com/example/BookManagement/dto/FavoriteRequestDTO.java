package com.example.BookManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRequestDTO {
    @NotNull
    private Integer bookId;
    private String title;
    private String author;
    private String category;
    private String image;
}
