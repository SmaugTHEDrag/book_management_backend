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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController
{
    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<UserPageResponse> getAllUsers(UserFilterForm form, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(form, pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        User user = userService.getUserById(id);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        UserDTO createUserDTO = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createUserDTO,HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserRequestDTO userRequestDTO){
        UserDTO updateUserDTO = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updateUserDTO);
    }

    @PutMapping("{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable int id, @RequestBody UpdateRoleDTO updateRoleDTO){
        try {
            userService.updateUserRole(id, updateRoleDTO);
            return ResponseEntity.ok("User role updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return new ResponseEntity<>("Id: "+id+" is deleted", HttpStatus.OK);
    }
}
