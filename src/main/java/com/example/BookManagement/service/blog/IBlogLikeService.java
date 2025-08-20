package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogLikeDTO;

public interface IBlogLikeService {
    BlogLikeDTO likeBlog(Integer blogId, String username);
    void unlikeBlog(Integer blogId, String username);
    long getLikeCount(Integer blogId);
    boolean hasUserLiked(Integer blogId, String username);
}
