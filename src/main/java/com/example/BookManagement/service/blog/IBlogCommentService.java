package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;

import java.util.List;

public interface IBlogCommentService {


    // Get all Blog comment (includes nested replies)
    List<BlogCommentDTO> getCommentsByBlog(Integer blogId);

    // Add a comment to a blog post
    BlogCommentDTO addComment(BlogCommentRequestDTO request, String username);

    // Update a comment
    BlogCommentDTO updateComment(Integer commentId, BlogCommentRequestDTO request, String username);

    // Delete a comment
    void deleteComment(Integer commentId, String username);

}
