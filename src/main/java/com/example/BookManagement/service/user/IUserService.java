package com.example.BookManagement.service.user;

import com.example.BookManagement.dto.user.UpdateRoleDTO;
import com.example.BookManagement.dto.user.UserDTO;
import com.example.BookManagement.dto.user.UserPageResponse;
import com.example.BookManagement.dto.user.UserRequestDTO;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.UserFilterForm;
import org.springframework.data.domain.Pageable;

public interface IUserService  {
    UserPageResponse getAllUsers(UserFilterForm form, Pageable pageable);

    User getUserById(int id);

    UserDTO createUser(UserRequestDTO userRequestDTO);

    UserDTO updateUser(int id, UserRequestDTO userRequestDTO);

    void deleteUser(int id);

    void updateUserRole(int id, UpdateRoleDTO updateRoleDTO);


}
