package com.example.BookManagement.controller;

import com.example.BookManagement.dto.review.ReviewDTO;
import com.example.BookManagement.dto.review.ReviewRequestDTO;
import com.example.BookManagement.service.review.IReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*
 * REST Controller for managing user's favorite books.
 * Handles adding, removing, and retrieving favorite books for the logged-in user.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Review API", description = "APIs for managing reviews of a book")
@RequestMapping("api/reviews")
public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping("book/{bookId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByBook(@PathVariable Integer bookId){
        return ResponseEntity.ok(reviewService.getReviewsByBook(bookId));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable String username){
        return ResponseEntity.ok(reviewService.getReviewsByUser(username));
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(Principal principal, @RequestBody @Valid ReviewRequestDTO requestDTO){
        return ResponseEntity.ok(reviewService.createReview(principal.getName(), requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Integer id, Principal principal, @RequestBody @Valid ReviewRequestDTO reviewRequestDTO){
        return ResponseEntity.ok(reviewService.updateReview(id, principal.getName(), reviewRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDTO> deleteReview(@PathVariable Integer id, Principal principal){
        reviewService.deleteReview(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

}
