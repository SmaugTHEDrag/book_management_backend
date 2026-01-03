package com.example.BookManagement.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BookFilterForm {

    // Filter by specific fields
    private String titleSearch;
    private String authorSearch;
    private String categorySearch;

    // ID range
    private Integer minId;
    private Integer maxId;

    // Global keyword search (title OR author OR category)
    private String search;
}
