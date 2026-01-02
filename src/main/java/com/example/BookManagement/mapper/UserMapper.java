package com.example.BookManagement.mapper;

import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.dto.user.UserRequestDTO;
import com.example.BookManagement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    User toEntity(UserRequestDTO userRequestDTO);

    void updateEntityFromDTO(UserRequestDTO userRequestDTO, @MappingTarget User existingUser);
}
