package com.example.BookManagement.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
 * Form object use for filtering book search results
 * Contains optional search parameters for title, author, category, ID range and general keyword search
 */
@Data
public class BookFilterForm {

    // Search books by title
    private String titleSearch;

    // Search books by author name
    private String authorSearch;

    // Search books by category
    private String categorySearch;

    // Minimum book ID for filtering
    private Integer minId;

    // Maximum book ID for filtering
    private Integer maxId;

    // General search keyword for title, author, or category
    private String search;
}
