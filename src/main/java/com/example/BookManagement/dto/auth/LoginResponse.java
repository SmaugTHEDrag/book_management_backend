package com.example.BookManagement.dto.auth;

import lombok.Data;

/*
 * Data Transfer Object (DTO) for login response
 * Used to send authentication information to the client after successful login
 */
@Data
public class LoginResponse {

    // Type of the token, default is "Bearer"
    private String type = "Bearer";

    // JWT token string for authentication
    private String token;

    // Username of the login user
    private String login;

    // Role of the login user (ADMIN or CUSTOMER)
    private String role;
}
