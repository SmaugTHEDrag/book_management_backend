package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Favorite;
import com.example.BookManagement.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IReviewRepository extends JpaRepository<Review, Integer> {

    // Get all reviews written by a specific user
    List<Review> findByUserId(Integer userId);

    // Get all reviews of specific book
    List<Review> findByBookId(Integer bookId);

    // Find a review by user and book (used to prevent duplicate reviews)
    Optional<Review> findByUserIdAndBookId(Integer userId, Integer bookId);

    // Check if a user has already reviewed a specific book
    boolean existsByUserIdAndBookId(Integer userId, Integer bookId);

    // Calculate average rating of a book
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.book.id = :bookId")
    Double findAvgRatingByBookId(@Param("bookId") Integer bookId);

    // Count total reviews of a book
    @Query("SELECT COUNT(r.id) FROM Review r WHERE r.book.id = :bookId")
    Integer getReviewCountByBookId(@Param("bookId") Integer bookId);

    // Get all reviews of a book with user info (avoid N+1 query)
    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.book.id = :bookId")
    List<Review> findByBookIdWithUser(@Param("bookId") Integer bookId);

}
