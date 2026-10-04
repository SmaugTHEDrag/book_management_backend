package com.example.BookManagement.review.service;

import com.example.BookManagement.review.dto.ReviewDTO;
import com.example.BookManagement.review.dto.ReviewRequestDTO;

import java.util.List;

public interface IReviewService {

    ReviewDTO createReview(ReviewRequestDTO request);

    // only review owner is allowed to update (enforced with @PreAuthorize in the controller)
    ReviewDTO updateReview(Integer id, ReviewRequestDTO request);

    // only review owner is allowed to delete (enforced with @PreAuthorize in the controller)
    void deleteReview(Integer id);

    List<ReviewDTO> getReviewsByBook(Integer bookId);

    List<ReviewDTO> getReviewsByUser(String username);
}

