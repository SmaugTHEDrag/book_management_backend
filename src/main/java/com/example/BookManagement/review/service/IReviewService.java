package com.example.BookManagement.review.service;

import com.example.BookManagement.review.dto.ReviewDTO;
import com.example.BookManagement.review.dto.ReviewRequestDTO;

import java.util.List;

public interface IReviewService {

    ReviewDTO createReview(String username, ReviewRequestDTO request);

    // only review owner is allowed to update
    ReviewDTO updateReview(Integer id, String username, ReviewRequestDTO request);

    // only review owner is allowed to delete
    void deleteReview(Integer id, String username);

    List<ReviewDTO> getReviewsByBook(Integer bookId);

    List<ReviewDTO> getReviewsByUser(String username);
}
