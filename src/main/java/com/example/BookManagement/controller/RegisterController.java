package com.example.BookManagement.controller;

import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.form.RegisterForm;
import com.example.BookManagement.service.auth.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller responsible for user registration.
 * Accepts user registration requests, validates input,
 * and registers a new user in the system.
 */
@RestController
@RequestMapping("api")
public class RegisterController {
    @Autowired
    private IAuthService userService; // Service layer to handle user registration logic

    /**
     * Registers a new user.
     *
     * @param registerForm the registration form containing user details (username, email, password)
     * @param bindingResult validation result for the request body
     * @return ResponseEntity with created UserDTO on success or validation errors on failure
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterForm registerForm, BindingResult bindingResult) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            // Attempt to register the user via service layer
            UserDTO createdUser = userService.register(registerForm);
            return ResponseEntity.ok(createdUser); // return created user DTO
        } catch (RuntimeException e) {
            // Handle exceptions such as username/email already taken
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
