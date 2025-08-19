package com.example.BookManagement.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookDTO {
    private Integer id;

    @NotBlank(message = "Title must not blank")
    @Size(min = 2, max = 255, message = "Title length must be between 2 and 255")
    private String title;

    @NotBlank(message = "Author must not blank")
    private String author;

    @NotBlank(message = "Category must not blank")
    private String category;

    @Pattern(regexp = "^(https?|ftp)://.*$", message = "Image must be a valid URL")
    private String image;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    @Pattern(regexp = "^(https?|ftp)://.*\\.pdf$", message = "PDF must be a valid PDF URL")
    private String pdf;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
