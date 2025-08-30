package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogLikeDTO;

/*
 * Service interface for managing likes on blogs.
 * Provides operations for liking, unliking, counting likes,
 * and checking if a user has liked a blog.
 */
public interface IBlogLikeService {
    /**
     * Like a blog for the given user.
     *
     * @param blogId   ID of the blog to like
     * @param username Username of the user who likes the blog
     * @return BlogLikeDTO containing like details
     * @throws RuntimeException if the blog is not found
     */
    BlogLikeDTO likeBlog(Integer blogId, String username);

    /**
     * Remove a user's like from a blog.
     *
     * @param blogId   ID of the blog to unlike
     * @param username Username of the user who unlikes the blog
     * @throws RuntimeException if the blog or like record is not found
     */
    void unlikeBlog(Integer blogId, String username);

    /**
     * Get the total number of likes for a specific blog.
     *
     * @param blogId ID of the blog
     * @return Total like count
     */
    long getLikeCount(Integer blogId);

    /**
     * Check if a specific user has liked a specific blog.
     *
     * @param blogId   ID of the blog
     * @param username Username of the user
     * @return true if the user has liked the blog, false otherwise
     */
    boolean hasUserLiked(Integer blogId, String username);
}
