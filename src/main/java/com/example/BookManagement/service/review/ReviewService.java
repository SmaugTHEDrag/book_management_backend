package com.example.BookManagement.service.review;

import com.example.BookManagement.dto.review.ReviewDTO;
import com.example.BookManagement.dto.review.ReviewRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.entity.Review;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.exception.ResourceNotFoundException;
import com.example.BookManagement.exception.ToxicContentException;
import com.example.BookManagement.mapper.ReviewMapper;
import com.example.BookManagement.repository.IBookRepository;
import com.example.BookManagement.repository.IReviewRepository;
import com.example.BookManagement.repository.IUserRepository;
import com.example.BookManagement.service.aimoderation.IAIModerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService{

    private final IReviewRepository reviewRepository;

    private final IBookRepository bookRepository;

    private final IUserRepository userRepository;

    private final ReviewMapper reviewMapper;

    private final IAIModerationService moderationService;

    // Get all reviews of a book
    @Override
    public List<ReviewDTO> getReviewsByBook(Integer bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return reviewMapper.toListDTO(reviews);
    }

    // Get all reviews of a user
    @Override
    public List<ReviewDTO> getReviewsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        List<Review> reviews = reviewRepository.findByUserId(user.getId());
        return reviewMapper.toListDTO(reviews);
    }

    // Create a new review for a book
    @Override
    public ReviewDTO createReview(String username, ReviewRequestDTO request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("username not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(()-> new ResourceNotFoundException("Book not found"));

        // Prevent duplicate review
        if(reviewRepository.existsByUserIdAndBookId(user.getId(), book.getId())){
            throw new IllegalStateException("You already reviewed this book");
        }

        // Check toxic content using AI moderation
        moderationService.checkComment(request.getComment(), "Review contains inappropriate content");

        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(savedReview);
    }

    // Update a review
    @Override
    public ReviewDTO updateReview(Integer id, String username, ReviewRequestDTO request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Review not found"));

        // Only review owner can update
        if (!review.getUser().getUsername().equals(username)) {
            throw new IllegalStateException("You are not allowed to update this review");
        }

        if(request.getRating() != null && (request.getRating()) != 0){
            review.setRating(request.getRating());
        }

        if(request.getComment() != null && !request.getComment().isBlank()){
            // Check toxic content before update
            moderationService.checkComment(request.getComment(), "Review contains inappropriate content");
            review.setComment(request.getComment());
        }

        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(updatedReview);
    }

    // Delete a review
    @Override
    public void deleteReview(Integer id, String username) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Review not found"));

        // Only review owner can delete
        if (!review.getUser().getUsername().equals(username)) {
            throw new IllegalStateException("You are not allowed to delete this review");
        }

        reviewRepository.delete(review);
    }

}
