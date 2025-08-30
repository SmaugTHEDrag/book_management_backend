package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;

import java.util.List;

/*
 * Service interface for managing blog comments.
 * Defines methods for adding, updating, deleting, and retrieving comments.
 */
public interface IBlogCommentService {
    /**
     * Add a comment to a blog post.
     *
     * @param blogId   ID of the blog to comment on
     * @param request  DTO containing comment content and optional parent comment ID
     * @param username Username of the user adding the comment
     * @return BlogCommentDTO containing the created comment details
     * @throws RuntimeException if the blog or user is not found
     */
    BlogCommentDTO addComment(Integer blogId, BlogCommentRequestDTO request, String username);

    /**
     * Update an existing comment.
     * Only the comment owner or an admin can update.
     *
     * @param commentId ID of the comment to update
     * @param request   DTO containing updated comment content
     * @param username  Username of the comment owner or admin
     * @return BlogCommentDTO containing the updated comment details
     * @throws RuntimeException     if the comment or user is not found
     */
    BlogCommentDTO updateComment(Integer commentId, BlogCommentRequestDTO request, String username);

    /**
     * Delete a comment.
     * Only the comment owner or an admin can delete.
     *
     * @param commentId ID of the comment to delete
     * @param username  Username of the comment owner or admin
     * @throws RuntimeException     if the comment or user is not found
     */
    void deleteComment(Integer commentId, String username);

    /**
     * Get all comments for a specific blog post.
     * Includes nested replies.
     *
     * @param blogId ID of the blog
     * @return List of BlogCommentDTOs for the blog
     * @throws RuntimeException if the blog is not found
     */
    List<BlogCommentDTO> getCommentsByBlog(Integer blogId);
}
