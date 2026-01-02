package com.example.BookManagement.mapper;

import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.RegisterForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    User toUserEntity(RegisterForm registerForm);

    UserDTO toDTO(User savedUser);
}
