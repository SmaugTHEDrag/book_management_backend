package com.example.BookManagement.form;

import lombok.Data;

@Data
public class UserFilterForm {

    // Filter by specific fields
    private String usernameSearch;
    private String emailSearch;
    private String roleSearch;

    // ID range
    private Integer minId;
    private Integer maxId;
}
