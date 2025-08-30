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

    /**
     * GET /api/blogs/{blogId}/likes/count
     * Get the total number of likes for a specific blog.
     *
     * @param blogId the ID of the blog
     * @return the count of likes
     */
    @GetMapping("/{blogId}/likes/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Integer blogId) {
        return ResponseEntity.ok(likeService.getLikeCount(blogId));
    }

    /**
     * GET /api/blogs/{blogId}/likes/has
     * Checked if the authenticated user has liked a specific blog.
     *
     * @param blogId the ID of the blog
     * @param principal the authenticated user
     * @return true if the user has liked the blog, false otherwise
     */
    @GetMapping("/{blogId}/likes/has")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(likeService.hasUserLiked(blogId, username));
    }

    /**
     * POST /api/blogs/{blogId}/likes
     * Like a blog. This operation is idempotent (liking multiple times does not duplicate).
     *
     * @param blogId the ID of the blog to like
     * @param principal the authenticated user performing the like
     * @return BlogLikeDTO representing the like action
     */
    @PostMapping("/{blogId}/likes")
    public ResponseEntity<BlogLikeDTO> likeBlog(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        BlogLikeDTO dto = likeService.likeBlog(blogId, username);
        return ResponseEntity.ok(dto);
    }

    /**
     * DELETE /api/blogs/{blogId}/likes
     * Remove a like from a blog.
     *
     * @param blogId the ID of the blog to unlike
     * @param principal the authenticated user performing the unlike
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{blogId}/likes")
    public ResponseEntity<Void> unlikeBlog(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        likeService.unlikeBlog(blogId, username);
        return ResponseEntity.noContent().build();
    }

}
