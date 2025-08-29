package com.example.BookManagement.service.auth;

import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.form.RegisterForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/*
 * Authentication service interface
 * Defines methods for user registration and login.
 */
public interface IAuthService extends UserDetailsService {

    /*
     * Register a new user
     * @param registerForm Data for registration
     * @return User info after registration
     */
    UserDTO register(RegisterForm registerForm);

    /*
     * Load user details by username (use by Spring Security for login)
     * @param username The username or email
     * @throws UsernameNotFoundException if the user is not found
     */
    UserDetails loadUserByUsername(String login) throws UsernameNotFoundException;
}
