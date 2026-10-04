package com.example.BookManagement.user.mapper;

import com.example.BookManagement.user.dto.UserDTO;
import com.example.BookManagement.user.dto.UserRequestDTO;
import com.example.BookManagement.user.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    // json null means "leave unchanged", not "set to null"
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(UserRequestDTO userRequestDTO, @MappingTarget User existingUser);
}

