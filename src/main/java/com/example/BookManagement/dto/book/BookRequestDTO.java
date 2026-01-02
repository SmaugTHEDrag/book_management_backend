package com.example.BookManagement.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    // Title of the book required length between 1 and 255 character
    @NotBlank(message = "Title must not be blank")
    @Size(min = 1, max = 255, message = "Title length must be between 2 and 255 characters")
    private String title;

    // Author of the book not blank
    @NotBlank(message = "Author must not be blank")
    private String author;

    // Category or genre of the book not blank
    @NotBlank(message = "Category must not be blank")
    private String category;

    // URL of the book cover image: optional, must be a valid URL if provided
    @Pattern(regexp = "^(https?|ftp)://.*$", message = "Image must be a valid URL")
    private String image;

    // Description of the book: optional, maximum 2000 characters
    @Size(max = 5000, message = "Description must be less than 2000 characters")
    private String description;

    // URL of the book's PDF file: optional, must end with .pdf if provided
    @Pattern(regexp = "^(https?|ftp)://.*\\.pdf$", message = "PDF must be a valid PDF URL")
    private String pdf;
}
