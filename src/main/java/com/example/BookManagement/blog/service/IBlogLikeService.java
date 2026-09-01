package com.example.BookManagement.blog.service;

import com.example.BookManagement.blog.dto.BlogLikeDTO;

public interface IBlogLikeService {

    BlogLikeDTO likeBlog(Integer blogId, String username);

    void unlikeBlog(Integer blogId, String username);

    long getLikeCount(Integer blogId);

    boolean hasUserLiked(Integer blogId, String username);
}
