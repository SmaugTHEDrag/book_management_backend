package com.example.BookManagement.form;

import lombok.Data;

/*
 * Form for filtering users when performing a search
 * Contains optional search parameters for username, email, role and ID range
 */
@Data
public class UserFilterForm {
    private String usernameSearch;
    private String emailSearch;
    private String roleSearch;
    private Integer minId;
    private Integer maxId;
}
