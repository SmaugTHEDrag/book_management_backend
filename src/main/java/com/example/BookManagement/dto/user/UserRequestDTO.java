package com.example.BookManagement.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * Data Transfer Object (DTO) for creating and updating a user
 * Receives input from client (request body) for user registration or admin operations
 * Validation ensures input data is correct before service processing
 */
@Data
public class UserRequestDTO {

    // Username of the user: required, 6-20 characters, letters, numbers, underscore allowed
    @NotBlank(message = "Username does not blank")
    @Size(min = 6, max = 20, message = "Username from 6 to 20 letters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username just have words, number, underline '_'")
    private String username;

    // Password of the user: required, 6-20 characters
    @NotBlank(message = "Password does not blank")
    @Size(min = 8, message = "Password must have at least 8 letters")
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message = "Password must have at least 1 upper letter"),
            @Pattern(regexp = ".*\\d.*", message = "Password must have at least 1 number"),
            @Pattern(regexp = ".*[!@#$%^&*()_+\\-=].*", message = "Password must have at least 1 special symbol")
    })
    private String password;

    // Email of the user: required, must be valid format
    @NotBlank(message = "Email does not blank")
    @Email(message = "Email is valid")
    private String email;

    // Role of the user: required, must be either ADMIN or CUSTOMER
    @NotBlank(message = "Role cannot be blank")
    @Pattern(regexp = "ADMIN|CUSTOMER", message = "Role must be either ADMIN or CUSTOMER")
    private String role;
}
