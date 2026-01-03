package com.example.BookManagement.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedAt;
}
