package com.example.BookManagement.dto.favorite;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * Data Transfer Object (DTO) for creating or updating a favorite record
 * Receives input from client (request body) when a user favorites a book
 * Validation ensures input data is correct before service processing
 */
@Data
public class FavoriteRequestDTO {

    // ID of the book to be favorite
    @NotNull(message = "Book ID can not be null")
    private Integer bookId;

    // Title of the book, max length 255 characters
    @Size(max = 255, message = "Title must be less that 255 characters")
    private String title;

    // Author of the book
    @Size(max = 50, message = "Author must be less than 50 characters")
    private String author;

    // Category or genre of the book
    @Size(max = 255, message = "Category must be less than 255 characters")
    private String category;

    // URL of the book cover image
    @Pattern(regexp = "^(https?|ftp)://.*$", message = "Image must be a valid URL")
    private String image;
}
