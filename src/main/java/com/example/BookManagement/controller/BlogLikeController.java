package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogLikeDTO;
import com.example.BookManagement.service.blog.IBlogLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/*
 * REST Controller for managing likes on blogs.
 * Provides endpoints to like, unlike, count likes, and check if a user has liked a blog.
 * Requires authentication for user-specific actions.
 */
@RestController
@RequestMapping("/api/blogs")
public class BlogLikeController {
    @Autowired
    private IBlogLikeService likeService; // Service layer handling blog like logic

    // Get like count for a blog
    @GetMapping("/{blogId}/likes/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Integer blogId) {
        return ResponseEntity.ok(likeService.getLikeCount(blogId));
    }

    // Check if current user liked the blog
    @GetMapping("/{blogId}/likes/has")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(likeService.hasUserLiked(blogId, username));
    }

    // Like a blog (idempotent)
    @PostMapping("/{blogId}/likes")
    public ResponseEntity<BlogLikeDTO> likeBlog(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        BlogLikeDTO dto = likeService.likeBlog(blogId, username);
        return ResponseEntity.ok(dto);
    }

    // Unlike a blog
    @DeleteMapping("/{blogId}/likes")
    public ResponseEntity<Void> unlikeBlog(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        likeService.unlikeBlog(blogId, username);
        return ResponseEntity.noContent().build();
    }

}
