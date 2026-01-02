package com.example.BookManagement.dto.book;

import com.example.BookManagement.dto.review.ReviewDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Integer id;
    private String title;
    private String author;
    private String category;
    private String image;
    private String description;
    private String pdf;

    // average rating (1–5)
    private Double avgRating;

    // total number of reviews
    private Integer reviewCount;

    // list of reviews (only for detail view)
    private List<ReviewDTO> reviews;
}
