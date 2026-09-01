package com.example.BookManagement.auth.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {

    @NotBlank(message = "Username or email must not blank")
    private String login;

    @NotBlank(message = "Password must not blank")
    private String password;
}
