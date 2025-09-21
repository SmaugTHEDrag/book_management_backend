package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;
import com.example.BookManagement.service.blog.IBlogCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Blog Comment API", description = "APIs for managing comments and replies on blogs")
public class BlogCommentController {

    @Autowired
    private IBlogCommentService commentService;  // Service layer handling comment logic

    // Get all comments (top-level + replies) for a blog
    @Operation(summary = "Get comments for a blog", description = "Returns all top-level and nested comments for a specific blog")
    @GetMapping("/{blogId}/comments")
    public ResponseEntity<List<BlogCommentDTO>> getComments(@PathVariable Integer blogId) {
        return ResponseEntity.ok(commentService.getCommentsByBlog(blogId));
    }

    // Add a new comment or reply to a blog
    @Operation(summary = "Add a comment", description = "Adds a new comment or reply to a blog. If parentCommentId is provided in the request DTO, it creates a reply.")
    @PostMapping("/{blogId}/comments")
    public ResponseEntity<BlogCommentDTO> addComment(@RequestBody BlogCommentRequestDTO request, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(commentService.addComment(request, username));
    }

    // Update an existing comment (Comment owner only)
    @Operation(summary = "Update comment", description = "Updates an existing comment. Only the comment owner can perform this action.")
    @PreAuthorize("@commentSecurity.canEdit(#commentId, authentication.name)")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<BlogCommentDTO> updateComment(@PathVariable Integer commentId, @RequestBody BlogCommentRequestDTO request, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(commentService.updateComment(commentId, request, username));
    }

    // Delete a comment (Blog owner, admin and Comment owner)
    @Operation(summary = "Delete comment", description = "Deletes a comment. Only the blog owner, admin, or comment owner can perform this action.")
    @PreAuthorize("hasAuthority('ADMIN') or @commentSecurity.canDelete(#commentId, authentication.name)")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId, Principal principal) {
        String username = principal.getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.noContent().build();
    }
}
