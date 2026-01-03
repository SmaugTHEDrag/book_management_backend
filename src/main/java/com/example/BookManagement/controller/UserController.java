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

@RestController
@RequestMapping("api/users")
@Tag(name = "User API", description = "APIs for users and roles")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    // get all users with filters and pagination (Admin only)
    @Operation(summary = "Get all users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<UserPageResponse> getAllUsers(UserFilterForm form, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(form, pageable));
    }

    // get user by ID
    @Operation(summary = "Get user by ID")
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // create a new user (Admin only)
    @Operation(summary = "Create a new user")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        UserDTO createUserDTO = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createUserDTO,HttpStatus.CREATED);
    }

    // update user info
    @Operation(summary = "Update user info")
    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody @Valid UserRequestDTO userRequestDTO){
        UserDTO updateUserDTO = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updateUserDTO);
    }

    // update user role (admin only)
    @Operation(summary = "Update user role")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable int id, @Valid @RequestBody UpdateRoleDTO updateRoleDTO,
                                            BindingResult bindingResult){
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

    // delete user by ID (Admin only)
    @Operation(summary = "Delete user")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return new ResponseEntity<>("Id: "+id+" is deleted", HttpStatus.OK);
    }
}
