package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;
import com.example.BookManagement.service.blog.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    /**
     * GET /api/blogs/{blogId}/comments
     * Retrieve all comments for a blog, including top-level comments and nested replies.
     *
     * @param blogId the ID of the blog
     * @return a list of BlogCommentDTOs representing comments and nested replies
     */
    @GetMapping("/{blogId}/comments")
    public ResponseEntity<List<BlogCommentDTO>> getComments(@PathVariable Integer blogId) {
        return ResponseEntity.ok(commentService.getCommentsByBlog(blogId));
    }

    /**
     * POST /api/blogs/{blogId}/comments
     * Add a new comment or reply to a blog.
     *
     * @param blogId the ID of the blog to comment on
     * @param request the comment content and optional parent comment ID for replies
     * @param principal the authenticated user performing the comment
     * @return the created BlogCommentDTO
     */
    @PostMapping("/{blogId}/comments")
    public ResponseEntity<BlogCommentDTO> addComment(@PathVariable Integer blogId, @RequestBody BlogCommentRequestDTO request,
                                                     Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(commentService.addComment(blogId, request, username));
    }

    /**
     * PUT /api/blogs/comments/{commentId}
     * Update an existing comment. Only the comment author (or admin in service logic) can update.
     *
     * @param commentId the ID of the comment to update
     * @param request the updated comment content
     * @param principal the authenticated user performing the update
     * @return the updated BlogCommentDTO
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<BlogCommentDTO> updateComment(@PathVariable Integer commentId,
                                                        @RequestBody BlogCommentRequestDTO request,
                                                        Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(commentService.updateComment(commentId, request, username));
    }

    /**
     * DELETE /api/blogs/comments/{commentId}
     * Delete a comment. Only the comment author (or admin in service logic) can delete.
     *
     * @param commentId the ID of the comment to delete
     * @param principal the authenticated user performing the deletion
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId,
                                              Principal principal) {
        String username = principal.getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.noContent().build();
    }
}
