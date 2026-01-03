package com.example.BookManagement.service.user;

import com.example.BookManagement.dto.user.UpdateRoleDTO;
import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.dto.user.UserPageResponse;
import com.example.BookManagement.dto.user.UserRequestDTO;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.UserFilterForm;
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
