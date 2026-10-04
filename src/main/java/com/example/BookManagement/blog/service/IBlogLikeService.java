package com.example.BookManagement.blog.service;

import com.example.BookManagement.blog.dto.BlogLikeDTO;

public interface IBlogLikeService {

    BlogLikeDTO likeBlog(Integer blogId);

    void unlikeBlog(Integer blogId);

    long getLikeCount(Integer blogId);

    boolean hasUserLiked(Integer blogId);
}

