package com.example.BookManagement.user.service;

import com.example.BookManagement.user.dto.UpdateRoleDTO;
import com.example.BookManagement.user.dto.UserDTO;
import com.example.BookManagement.user.dto.UserPageResponse;
import com.example.BookManagement.user.dto.UserRequestDTO;
import com.example.BookManagement.user.entity.Role;
import com.example.BookManagement.user.entity.User;
import com.example.BookManagement.common.exception.ResourceNotFoundException;
import com.example.BookManagement.user.form.UserFilterForm;
import com.example.BookManagement.user.mapper.UserMapper;
import com.example.BookManagement.user.repository.IUserRepository;
import com.example.BookManagement.user.specification.UserSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService{

    private final IUserRepository userRepository;

    private final UserMapper userMapper;

    // Get users with pagination and filter
    @Override
    public UserPageResponse getAllUsers(UserFilterForm form, Pageable pageable) {
        Specification<User> where = UserSpecification.buildWhere(form);
        Page<User> users = userRepository.findAll(where,pageable);
        Page<UserDTO> userDTOS = users.map(userMapper::toDTO);

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

    // Get a user by ID
    @Override
    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        return userMapper.toDTO(user);
    }

    // Creates new user.
    @Override
    public UserDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    // Update user information
    @Override
    public UserDTO updateUser(int id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+id));

        userMapper.updateEntityFromDTO(userRequestDTO, existingUser);
        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDTO(updatedUser);
    }

    // Deletes user by ID
    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    // Update user role
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
