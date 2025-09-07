package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogLikeDTO;

/*
 * Service interface for managing likes on blogs
 * Provides operations for liking, unliking, counting likes,
 * and checking if a user has liked a blog
 */
public interface IBlogLikeService {

    // Like a blog for the given user
    BlogLikeDTO likeBlog(Integer blogId, String username);

    // Remove a user's like from a blog
    void unlikeBlog(Integer blogId, String username);

    // Get the total number of likes for a specific blog
    long getLikeCount(Integer blogId);

    // Check if a specific user has liked a specific blog
    boolean hasUserLiked(Integer blogId, String username);
}
