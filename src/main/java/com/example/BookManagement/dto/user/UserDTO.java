package com.example.BookManagement.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * Data Transfer Object (DTO) for User entity
 * Used to send user information between backend and client
 * Validation ensures data is correct when receiving input from client
 */
@Data
public class UserDTO {

    // User ID
    private Integer id;

    // Username of the user
    private String username;

    // User's email address
    private String email;

    // Role of the user, ADMIN or CUSTOMER
    private String role;

    // Account creation date (format not enforce here)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdAt;

    // Account last update date (format not enforce here)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedAt;
}
