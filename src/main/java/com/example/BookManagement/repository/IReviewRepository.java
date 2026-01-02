package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Favorite;
import com.example.BookManagement.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IReviewRepository extends JpaRepository<Review, Integer> {

    // Get all favorite books of specific user
    List<Review> findByUserId(Integer userId);

    // Get all reviews of specific book
    List<Review> findByBookId(Integer bookId);

    // Find a specific favorite entry by user ID and book ID
    Optional<Review> findByUserIdAndBookId(Integer userId, Integer bookId);

    // Check if a user has already added favorite a specific book
    boolean existsByUserIdAndBookId(Integer userId, Integer bookId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.book.id = :bookId")
    Double findAvgRatingByBookId(@Param("bookId") Integer bookId);

    @Query("SELECT COUNT(r.id) FROM Review r WHERE r.book.id = :bookId")
    Integer getReviewCountByBookId(@Param("bookId") Integer bookId);

    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.book.id = :bookId")
    List<Review> findByBookIdWithUser(@Param("bookId") Integer bookId);

}
