package com.example.BookManagement.user.mapper;

import com.example.BookManagement.user.dto.UserDTO;
import com.example.BookManagement.user.dto.UserRequestDTO;
import com.example.BookManagement.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    User toEntity(UserRequestDTO userRequestDTO);

    void updateEntityFromDTO(UserRequestDTO userRequestDTO, @MappingTarget User existingUser);
}
