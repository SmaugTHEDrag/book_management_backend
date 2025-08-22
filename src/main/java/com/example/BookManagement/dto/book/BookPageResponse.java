package com.example.BookManagement.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * Data Transfer Object (DTO) for paginated response of BookDTO
 * Used to return a page of book along with pagination metadata.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPageResponse {

    // List of books in the current page
    private List<BookDTO> content;

    // Current page number (0-based depending on implementation
    private int currentPage;

    // Total number of books in the database
    private long totalItems;

    // Total number of pages available
    private int totalPages;

    // Number of book per page
    private int pageSize;

    // Flag indicating if this is the last page
    private boolean isLast;

    // Flag indicating if this is the first page
    private boolean isFirst;
}
