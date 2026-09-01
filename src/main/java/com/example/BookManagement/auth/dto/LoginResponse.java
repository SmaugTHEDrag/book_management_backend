package com.example.BookManagement.auth.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String type = "Bearer";
    private String token;
    private String login;
    private String role;
}
