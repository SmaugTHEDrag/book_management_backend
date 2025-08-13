package com.example.BookManagement.service;

import com.example.BookManagement.dto.UpdateRoleDTO;
import com.example.BookManagement.dto.UserDTO;
import com.example.BookManagement.dto.UserRequestDTO;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.RegisterForm;
import com.example.BookManagement.form.UserFilterForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService  {
    List<User> getAllUsers(UserFilterForm form);

    User getUserById(int id);

    UserDTO createUser(UserRequestDTO userRequestDTO);

    UserDTO updateUser(int id, UserRequestDTO userRequestDTO);

    void deleteUser(int id);

    void updateUserRole(int id, UpdateRoleDTO updateRoleDTO);


}
