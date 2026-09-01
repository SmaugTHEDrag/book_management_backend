package com.example.BookManagement.user.service;

import com.example.BookManagement.user.dto.UpdateRoleDTO;
import com.example.BookManagement.user.dto.UserDTO;
import com.example.BookManagement.user.dto.UserPageResponse;
import com.example.BookManagement.user.dto.UserRequestDTO;
import com.example.BookManagement.user.form.UserFilterForm;
import org.springframework.data.domain.Pageable;

public interface IUserService  {

    // Get paginated user with filters
    UserPageResponse getAllUsers(UserFilterForm form, Pageable pageable);

    // Get a user by ID
    UserDTO getUserById(int id);

    // Create a new user
    UserDTO createUser(UserRequestDTO userRequestDTO);

    // Update an existing user
    UserDTO updateUser(int id, UserRequestDTO userRequestDTO);

    // Delete a user by ID
    void deleteUser(int id);

    // Update a user's role
    void updateUserRole(int id, UpdateRoleDTO updateRoleDTO);


}
