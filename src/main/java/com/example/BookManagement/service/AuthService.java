package com.example.BookManagement.service;

import com.example.BookManagement.dto.UserDTO;
import com.example.BookManagement.entity.Role;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.form.RegisterForm;
import com.example.BookManagement.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO register(RegisterForm registerForm) {
        if (userRepository.existsByUsername(registerForm.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(registerForm.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = modelMapper.map(registerForm, User.class);
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        if (registerForm.getRole() != null && !registerForm.getRole().isEmpty()) {
            user.setRole(Role.valueOf(registerForm.getRole().toUpperCase()));
        } else {
            user.setRole(Role.CUSTOMER);
        }
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = login.contains("@")
                ? userRepository.findByEmail(login).orElseThrow(() -> new UsernameNotFoundException("Email not found"))
                : userRepository.findByUsername(login).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole().toString())
        );
    }
}
