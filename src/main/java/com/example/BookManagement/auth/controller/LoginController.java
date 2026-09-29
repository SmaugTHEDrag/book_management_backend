package com.example.BookManagement.auth.controller;

import com.example.BookManagement.auth.dto.LoginResponse;
import com.example.BookManagement.auth.form.LoginForm;
import com.example.BookManagement.auth.service.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@Tag(name = "Auth API", description = "API for user login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthServiceImpl authService;

    @Operation(summary = "User login via Keycloak")
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginForm loginForm) {
        LoginResponse response = authService.proxyLogin(loginForm);
        return ResponseEntity.ok(response);
    }
}