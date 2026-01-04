package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;

import java.util.List;

public interface IBlogCommentService {

    List<BlogCommentDTO> getCommentsByBlog(Integer blogId);

    // add new comment or reply
    BlogCommentDTO addComment(BlogCommentRequestDTO request, String username);

    // only comment owner can update
    BlogCommentDTO updateComment(Integer commentId, BlogCommentRequestDTO request, String username);

    // privilege check is required before delete
    void deleteComment(Integer commentId, String username);

}
