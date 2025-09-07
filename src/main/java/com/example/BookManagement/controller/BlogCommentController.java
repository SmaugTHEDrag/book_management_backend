package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;
import com.example.BookManagement.service.blog.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*
 * REST Controller for managing comments on blogs.
 * Supports creating, updating, deleting, and retrieving comments.
 * Replies (nested comments) are supported through parentCommentId in the request DTO.
 */
@RestController
@RequestMapping("/api/blogs")
public class BlogCommentController {

    @Autowired
    private IBlogCommentService commentService;  // Service layer handling comment logic

    // Get all comments (top-level + replies) for a blog
    @GetMapping("/{blogId}/comments")
    public ResponseEntity<List<BlogCommentDTO>> getComments(@PathVariable Integer blogId) {
        return ResponseEntity.ok(commentService.getCommentsByBlog(blogId));
    }

    // Add a new comment or reply to a blog.
    @PostMapping("/{blogId}/comments")
    public ResponseEntity<BlogCommentDTO> addComment(@RequestBody BlogCommentRequestDTO request,
                                                     Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(commentService.addComment(request, username));
    }

    // Update an existing comment (Comment owner only)
    @PreAuthorize("@commentSecurity.canEdit(#id, authentication.name)")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<BlogCommentDTO> updateComment(@PathVariable Integer commentId,
                                                        @RequestBody BlogCommentRequestDTO request,
                                                        Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(commentService.updateComment(commentId, request, username));
    }

    // Delete a comment (Blog owner, admin and Comment owner)
    @PreAuthorize("hasAuthority('ADMIN') or @commentSecurity.canDelete(#id, authentication.name)")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId,
                                              Principal principal) {
        String username = principal.getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.noContent().build();
    }
}
