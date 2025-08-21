package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
* Repository interface for Favorite entity
* Provide CRUD operation and custom queries for managing user favorites
*
* JpaRepository gives basic CRUD methods (save, findById, findAll, delete, etc.).
 */
public interface IFavoriteRepository extends JpaRepository<Favorite, Integer> {

    // Get all favorite books of specific user
    List<Favorite> findByUserId(Integer userId);

    // Find a specific favorite entry by user ID and book ID
    Optional<Favorite> findByUserIdAndBookId(Integer userId, Integer bookId);

    // Check if a user has already added favorite a specific book
    boolean existsByUserIdAndBookId(Integer userId, Integer bookId);
}
