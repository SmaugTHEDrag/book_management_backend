package com.example.BookManagement.controller;

import com.example.BookManagement.dto.user.UpdateRoleDTO;
import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.dto.user.UserPageResponse;
import com.example.BookManagement.dto.user.UserRequestDTO;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.UserFilterForm;
import com.example.BookManagement.service.user.IUserService;
import jakarta.validation.Valid;
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
public class UserController
{
    @Autowired
    private IUserService userService; // Service layer for user operations

    @Autowired
    private ModelMapper modelMapper;  // Maps entity objects to DTOs

    /**
     * Get all users with optional filters and pagination.
     * Only accessible by ADMIN users.
     *
     * @param form     filter form (username, email, role, etc.)
     * @param pageable pagination and sorting information
     * @return paginated user data
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<UserPageResponse> getAllUsers(UserFilterForm form, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(form, pageable));
    }

    /**
     * Get a single user by ID.
     * Accessible by any authenticated user.
     *
     * @param id user ID
     * @return user data mapped to UserDTO
     */
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        User user = userService.getUserById(id);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Create a new user.
     * Only ADMIN users can create other users.
     *
     * @param userRequestDTO incoming request DTO with user data
     * @return created user data with HTTP 201 status
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        UserDTO createUserDTO = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createUserDTO,HttpStatus.CREATED);
    }

    /**
     * Update an existing user's information.
     * Accessible by the user themselves or by ADMINs (depends on service logic).
     *
     * @param id             user ID
     * @param userRequestDTO DTO containing updated fields
     * @return updated user data
     */
    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserRequestDTO userRequestDTO){
        UserDTO updateUserDTO = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updateUserDTO);
    }

    /**
     * Update a user's role (e.g., USER -> ADMIN).
     * Only ADMIN users can update roles.
     *
     * @param id             user ID
     * @param updateRoleDTO  DTO with the new role
     * @param bindingResult  validation result
     * @return success message or validation errors
     */
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

    /**
     * Delete a user by ID.
     * Only ADMIN users can delete other users.
     *
     * @param id user ID
     * @return success message
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return new ResponseEntity<>("Id: "+id+" is deleted", HttpStatus.OK);
    }
}
