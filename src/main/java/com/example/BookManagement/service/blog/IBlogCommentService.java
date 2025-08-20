package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;

import java.util.List;

public interface IBlogCommentService {
    BlogCommentDTO addComment(Integer blogId, BlogCommentRequestDTO request, String username);
    BlogCommentDTO updateComment(Integer commentId, BlogCommentRequestDTO request, String username);
    void deleteComment(Integer commentId, String username);
    List<BlogCommentDTO> getCommentsByBlog(Integer blogId);
}
