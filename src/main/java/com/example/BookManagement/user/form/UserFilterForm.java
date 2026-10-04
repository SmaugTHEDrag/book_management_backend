package com.example.BookManagement.user.form;

import lombok.Data;

@Data
public class UserFilterForm {

    // Search by specific fields
    private String usernameSearch;
    private String emailSearch;
    private String statusSearch;

    // ID range
    private Integer minId;
    private Integer maxId;
}

