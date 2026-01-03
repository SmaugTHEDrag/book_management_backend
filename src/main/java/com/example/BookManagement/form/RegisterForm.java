package com.example.BookManagement.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {

    @NotBlank(message = "Username must not be blank")
    @Size(min = 6, max = 20, message = "Username must be between 6 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores ")
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least 1 upper letter"),
            @Pattern(regexp = ".*\\d.*", message = "Password must contain at least 1 number"),
            @Pattern(regexp = ".*[!@#$%^&*()_+\\-=].*", message = "Password must contain at least 1 special symbol")
    })
    private String password;

    @Pattern(regexp = "CUSTOMER", message = "Only CUSTOMER role is allowed")
    private String role;
}
