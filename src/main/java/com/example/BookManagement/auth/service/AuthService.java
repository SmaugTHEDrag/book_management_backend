package com.example.BookManagement.auth.service;

import com.example.BookManagement.user.dto.UserDTO;
import com.example.BookManagement.auth.form.RegisterForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    UserDTO register(RegisterForm registerForm);
    
}
