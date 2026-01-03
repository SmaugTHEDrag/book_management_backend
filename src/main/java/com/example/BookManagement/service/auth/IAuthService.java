package com.example.BookManagement.service.auth;

import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.form.RegisterForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IAuthService extends UserDetailsService {

    // Register a new user
    UserDTO register(RegisterForm registerForm);
    
}
