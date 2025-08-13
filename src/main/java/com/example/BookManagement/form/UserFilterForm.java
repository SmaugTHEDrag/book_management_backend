package com.example.BookManagement.form;

import lombok.Data;

@Data
public class UserFilterForm {
    private String usernameSearch;
    private String emailSearch;
    private String roleSearch;
    private Integer minId;
    private Integer maxId;
}
