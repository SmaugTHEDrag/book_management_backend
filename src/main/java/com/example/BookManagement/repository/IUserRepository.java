package com.example.BookManagement.repository;

import com.example.BookManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/*
* Repository interface for User entity
* Provides methods to perform CRUD operations
* as well as custom queries like finding by username or email
* and checking if a username or email already exist
*
* JpaRepository gives basic CRUD methods (save, findById, findAll, delete, etc.).
* JpaSpecificationExecutor allows building dynamic queries with specifications.
 */
public interface IUserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    // Find a user by their username
    Optional<User> findByUsername(String username);

    // Find a user by their email
    Optional<User> findByEmail(String email);

    // Check if a user with the given username already exists
    boolean existsByUsername(String username);

    // Check if a user with the given email already exists
    boolean existsByEmail(String email);
}
