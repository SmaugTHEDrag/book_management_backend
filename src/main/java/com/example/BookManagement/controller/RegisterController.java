package com.example.BookManagement.controller;

import com.example.BookManagement.dto.UserDTO;
import com.example.BookManagement.dto.UserRequestDTO;
import com.example.BookManagement.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class RegisterController {
    @Autowired
    private IUserService userService;

    @PostMapping("register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(userService.register(userRequestDTO));
    }
}
