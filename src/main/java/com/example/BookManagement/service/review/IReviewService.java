package com.example.BookManagement.service.review;

import com.example.BookManagement.dto.review.ReviewDTO;
import com.example.BookManagement.dto.review.ReviewRequestDTO;

import java.util.List;

public interface IReviewService {
    // User thêm review cho 1 book
    ReviewDTO createReview(String username, ReviewRequestDTO request);

    // User sửa review của chính mình
    ReviewDTO updateReview(Integer id, String username, ReviewRequestDTO request);

    // User xoá review của chính mình
    void deleteReview(Integer id, String username);

    // Xem tất cả review của 1 book
    List<ReviewDTO> getReviewsByBook(Integer bookId);

    // (Optional) Xem review của chính user
    List<ReviewDTO> getReviewsByUser(String username);
}
