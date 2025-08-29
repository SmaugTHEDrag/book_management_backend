package com.example.BookManagement.service.user;

import com.example.BookManagement.dto.user.UpdateRoleDTO;
import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.dto.user.UserPageResponse;
import com.example.BookManagement.dto.user.UserRequestDTO;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.UserFilterForm;
import org.springframework.data.domain.Pageable;

/*
 * Service interface for managing user-related operations.
 * Defines methods for CRUD operations and role updates.
 */
public interface IUserService  {

    /**
     * Retrieve all users with filtering and pagination
     *
     * @param form filter criteria (username, email, role, etc.)
     * @param pageable pagination and sorting information
     * @return paginated list of users
     */
    UserPageResponse getAllUsers(UserFilterForm form, Pageable pageable);

    /**
     * Get user details by ID
     *
     * @param id user ID
     * @return User entity
     */
    User getUserById(int id);

    /**
     * Create a new user
     *
     * @param userRequestDTO DTO containing user creation data
     * @return created user as DTO
     */
    UserDTO createUser(UserRequestDTO userRequestDTO);

    /**
     * Update existing user information
     *
     * @param id user ID
     * @param userRequestDTO DTO containing updated data
     * @return updated user as DTO
     */
    UserDTO updateUser(int id, UserRequestDTO userRequestDTO);

    /**
     * Delete a user by ID
     *
     * @param id user ID
     */
    void deleteUser(int id);

    /**
     * Update a user's role
     *
     * @param id user ID
     * @param updateRoleDTO DTO containing new role
     */
    void updateUserRole(int id, UpdateRoleDTO updateRoleDTO);


}
