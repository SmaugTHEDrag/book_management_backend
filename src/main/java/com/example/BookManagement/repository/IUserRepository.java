package com.example.BookManagement.repository;

import com.example.BookManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    // find user by username
    Optional<User> findByUsername(String username);

    // find user by email
    Optional<User> findByEmail(String email);

    // check if username exists
    boolean existsByUsername(String username);

    // check if email exists
    boolean existsByEmail(String email);
}
