package com.example.BookManagement.service.review;

import com.example.BookManagement.dto.review.ReviewDTO;
import com.example.BookManagement.dto.review.ReviewRequestDTO;

import java.util.List;

public interface IReviewService {
    // Create a review for a book
    ReviewDTO createReview(String username, ReviewRequestDTO request);

    // Update user's own review
    ReviewDTO updateReview(Integer id, String username, ReviewRequestDTO request);

    // Delete user's own review
    void deleteReview(Integer id, String username);

    // Get all reviews of a book
    List<ReviewDTO> getReviewsByBook(Integer bookId);

    // Get all reviews written by a user (optional)
    List<ReviewDTO> getReviewsByUser(String username);
}
