package com.example.BookManagement.service.user;

import com.example.BookManagement.dto.user.UpdateRoleDTO;
import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.dto.user.UserPageResponse;
import com.example.BookManagement.dto.user.UserRequestDTO;
import com.example.BookManagement.entity.Role;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.exception.ResourceNotFoundException;
import com.example.BookManagement.form.UserFilterForm;
import com.example.BookManagement.repository.IUserRepository;
import com.example.BookManagement.specification.UserSpecification;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * Service implementation for managing user-related operations.
 * This class provides business logic for:
 * - Fetching users with filtering & pagination
 * - Creating, updating, and deleting users
 * - Updating user roles
 */
@Service
@Transactional
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all users based on filter form and pagination info

     * @param form filter criteria (username, email, role, etc.)
     * @param pageable pagination and sorting information
     * @return paginated response containing a list of {@link UserDTO}
     */
    @Override
    public UserPageResponse getAllUsers(UserFilterForm form, Pageable pageable) {
        Specification<User> where = UserSpecification.buildWhere(form);
        Page<User> users = userRepository.findAll(where,pageable);
        Page<UserDTO> userDTOS = users.map(user -> modelMapper.map(user, UserDTO.class));

        //Build response
        return new UserPageResponse(
                userDTOS.getContent(),
                userDTOS.getNumber(),
                userDTOS.getTotalElements(),
                userDTOS.getTotalPages(),
                userDTOS.getSize(),
                userDTOS.isLast(),
                userDTOS.isFirst()
        );
    }

    /**
     * Finds a user by their ID

     * @param id user ID
     * @return User entity if found, otherwise null
     */
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Creates a new user.

     * @param userRequestDTO DTO containing new user data
     * @return created {@link UserDTO}
     */
    @Override
    public UserDTO createUser(UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);
        // Store
        User savedUser = userRepository.save(user);
        // Return DTO
        return modelMapper.map(savedUser, UserDTO.class);
    }

    /**
     * Updates an existing user by ID

     * @param id user ID
     * @param userRequestDTO DTO containing updated user data
     * @return updated {@link UserDTO}
     * @throws ResourceNotFoundException if user is not found
     */
    @Override
    public UserDTO updateUser(int id, UserRequestDTO userRequestDTO) {
        // Check if user exist
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+id));
        // Map new data from DTO to entity
        modelMapper.map(userRequestDTO, existingUser);
        // Store it
        User updatedUser = userRepository.save(existingUser);
        // Return DTO
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    /**
     * Deletes a user by ID

     * @param id user ID
     */
    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    /**
     * Updates the role of a user

     * @param id user ID
     * @param updateRoleDTO DTO containing the new role
     * @throws RuntimeException if user is not found
     */
    @Override
    public void updateUserRole(int id, UpdateRoleDTO updateRoleDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + id);
        }

        User user = optionalUser.get();
        user.setRole(Role.valueOf(updateRoleDTO.getRole()));
        userRepository.save(user);
    }

}
