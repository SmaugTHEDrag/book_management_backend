package com.example.BookManagement.controller;

import com.example.BookManagement.dto.user.UpdateRoleDTO;
import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.dto.user.UserPageResponse;
import com.example.BookManagement.dto.user.UserRequestDTO;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.UserFilterForm;
import com.example.BookManagement.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
 * REST Controller for user management.
 * Handles CRUD operations and role updates.
 */
@RestController
@RequestMapping("api/users")
@Tag(name = "User API", description = "APIs for managing users and roles")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService; // Service layer for user operations

    private final ModelMapper modelMapper;  // Maps entity objects to DTOs

    // Get all users with filters and pagination (Admin only)
    @Operation(summary = "Get all users", description = "Retrieve all users with optional filters and pagination (Admin only)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<UserPageResponse> getAllUsers(UserFilterForm form, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(form, pageable));
    }

    // Get user by ID
    @Operation(summary = "Get user by ID", description = "Retrieve a single user by their ID")
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        User user = userService.getUserById(id);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    // Create a new user (Admin only)
    @Operation(summary = "Create a new user", description = "Create a new user (Admin only)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        UserDTO createUserDTO = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createUserDTO,HttpStatus.CREATED);
    }

    // Update user info
    @Operation(summary = "Update user info", description = "Update user information by ID")
    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody @Valid UserRequestDTO userRequestDTO){
        UserDTO updateUserDTO = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updateUserDTO);
    }

    // Update user role CUSTOMER -> ADMIN or ADMIN -> CUSTOMER (Admin only)
    @Operation(summary = "Update user role", description = "Update user role between CUSTOMER and ADMIN (Admin only)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable int id, @Valid @RequestBody UpdateRoleDTO updateRoleDTO,
                                            BindingResult bindingResult){
        // Handle validation errors
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            userService.updateUserRole(id, updateRoleDTO);
            return ResponseEntity.ok("User role updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete user by ID (Admin only)
    @Operation(summary = "Delete user", description = "Delete a user by ID (Admin only)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return new ResponseEntity<>("Id: "+id+" is deleted", HttpStatus.OK);
    }
}
