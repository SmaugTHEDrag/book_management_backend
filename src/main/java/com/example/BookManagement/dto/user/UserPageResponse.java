package com.example.BookManagement.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * Data Transfer Object (DTO) for paginated response of UserDTO
 * Used to return a page of users along with pagination metadata
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageResponse {

    // List of users in the current page
    private List<UserDTO> content;

    // Current page number(0-based depending on implementation
    private int currentPage;

    // Total number of users in the database
    private long totalItems;

    // Total number of pages available
    private int totalPages;

    // Number of items per page
    private int pageSize;

    // Flag indicating if this is the last page
    private boolean isLast;

    // Flag indicating if this is the first page
    private boolean isFirst;
}
