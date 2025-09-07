package com.example.BookManagement.form;

import lombok.Data;

/*
 * Form for filtering users when performing a search
 * Contains optional search parameters for username, email, role and ID range
 */
@Data
public class UserFilterForm {

    // Search user by username
    private String usernameSearch;

    // Search user by email
    private String emailSearch;

    // Search users by their role
    private String roleSearch;

    // Minimum book ID for filtering
    private Integer minId;

    // Maximum book ID for filtering
    private Integer maxId;
}
