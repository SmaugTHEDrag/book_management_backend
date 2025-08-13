package com.example.BookManagement.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {
    @NotBlank(message = "Username does not blank")
    @Size(min = 6, max = 20, message = "Username from 6 to 20 letters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username just have words, number, underline '_'")
    private String username;

    @NotBlank(message = "Email does not blank")
    @Email(message = "Email is valid")
    private String email;

    @NotBlank(message = "Password does not blank")
    @Size(min = 8, message = "Password must have at least 8 letters")
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message = "Password must have at least 1 upper letter"),
            @Pattern(regexp = ".*\\d.*", message = "Password must have at least 1 number"),
            @Pattern(regexp = ".*[!@#$%^&*()_+\\-=].*", message = "Password must have at least 1 special symbol")
    })
    private String password;

    private String role;
}
