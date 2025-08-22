package com.example.BookManagement.dto.favorite;

import lombok.Data;

/*
 * Data Transfer Object (DTO) for Favorite entity
 * Used to transfer favorite book information between backend and client
 */
@Data
public class FavoriteDTO {

    // Favorite ID
    private Integer id;

    // ID of the user who add the book to favorite
    private Integer userId;

    // ID of the book that is added
    private Integer bookId;

    // Title of the favorite book
    private String title;

    // Author of the favorite book
    private String author;

    // Category or genre of the favorite book
    private String category;

    // URL of the favorite book's cover image
    private String image;
}
