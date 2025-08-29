package com.example.BookManagement.service.auth;

import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.entity.Role;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.RegisterForm;
import com.example.BookManagement.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * Implementation of authentication service
 * Handles user registration and login for Spring Security
 */
@Service
public class AuthService implements IAuthService{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /*
     * Registers a new user with validation for duplicate username/email.
     * Sets default role as CUSTOMER if not provided.
     * @param registerForm form containing username, email, password, and optional role
     * @return UserDTO containing the registered user's details
     * @throws RuntimeException if the username or email already exists
     */
    @Override
    public UserDTO register(RegisterForm registerForm) {
        // Check for duplicate username
        if (userRepository.existsByUsername(registerForm.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        // Check for duplicate email
        if (userRepository.existsByEmail(registerForm.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Map RegisterForm -> User entity
        User user = modelMapper.map(registerForm, User.class);

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));

        // Set role (default: CUSTOMER)
        if (registerForm.getRole() != null && !registerForm.getRole().isEmpty()) {
            user.setRole(Role.valueOf(registerForm.getRole().toUpperCase()));
        } else {
            user.setRole(Role.CUSTOMER);
        }

        // Save user and return DTO
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    /*
     * Loads a user by their username or email for Spring Security authentication.
     *
     * @param login username or email
     * @return UserDetails object for Spring Security authentication
     * @throws UsernameNotFoundException if no user is found with the given username or email
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Find user by email if login contains '@', otherwise by username
        User user = login.contains("@")
                ? userRepository.findByEmail(login).orElseThrow(() -> new UsernameNotFoundException("Email not found"))
                : userRepository.findByUsername(login).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        // Return Spring Security User object
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole().toString())
        );
    }
}
