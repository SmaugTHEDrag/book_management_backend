package com.example.BookManagement.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * Form object for handling user login requests
 * Contains basic validation rules for username, email, and password
 */
@Data
public class LoginForm {

    // Can be username or email
    @NotBlank(message = "Username or email must not blank")
    private String login;

    // Raw password of user
    @NotBlank(message = "Password must not blank")
    private String password;
}
