package com.example.BookManagement.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BookFilterForm {
    private String titleSearch;
    private String authorSearch;
    private String categorySearch;
    private Integer minId;
    private Integer maxId;
}
