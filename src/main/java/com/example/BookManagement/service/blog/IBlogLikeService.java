package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogLikeDTO;

public interface IBlogLikeService {

    // Like a blog
    BlogLikeDTO likeBlog(Integer blogId, String username);

    // Unlike a blog
    void unlikeBlog(Integer blogId, String username);

    // Count likes of a blog
    long getLikeCount(Integer blogId);

    // Check if user liked the blog
    boolean hasUserLiked(Integer blogId, String username);
}
