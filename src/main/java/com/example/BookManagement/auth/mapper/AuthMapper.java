package com.example.BookManagement.auth.mapper;

import com.example.BookManagement.user.dto.UserDTO;
import com.example.BookManagement.user.entity.User;
import com.example.BookManagement.auth.form.RegisterForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    User toUserEntity(RegisterForm registerForm);

    UserDTO toDTO(User savedUser);
}
