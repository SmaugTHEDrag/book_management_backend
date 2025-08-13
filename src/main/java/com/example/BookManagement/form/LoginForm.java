package com.example.BookManagement.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginForm {

    @NotBlank(message = "Username or email must not blank")
    private String login;

    @NotBlank(message = "Password must not blank")
    private String password;
}
