package com.example.BookManagement.service;

import com.example.BookManagement.dto.UserDTO;
import com.example.BookManagement.form.RegisterForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IAuthService extends UserDetailsService {
    UserDTO register(RegisterForm registerForm);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
