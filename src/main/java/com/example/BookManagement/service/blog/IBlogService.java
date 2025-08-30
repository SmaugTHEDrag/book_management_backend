package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.entity.Blog;

import java.util.List;

/*
 * Service interface for managing blogs.
 * Defines methods for creating, updating, retrieving, and deleting blogs.
 */
public interface IBlogService {

    /**
     * Get a list of all blogs.
     *
     * @return list of BlogDTO containing blog details
     */
    List<BlogDTO> getAllBlogs();

    /**
     * Get details of a specific blog by ID.
     *
     * @param id blog ID
     * @return BlogDTO containing the blog details
     * @throws RuntimeException if blog is not found
     */
    BlogDTO getBlogById(int id);

    /**
     * Create a new blog for the given user.
     *
     * @param requestDTO DTO containing the blog title, content, etc.
     * @param username   username of the blog owner
     * @return BlogDTO containing the created blog details
     * @throws RuntimeException if user is not found
     */
    BlogDTO createBlog(BlogRequestDTO requestDTO, String username);


    /**
     * Update an existing blog.
     * Only the blog owner should be allowed to update.
     *
     * @param id         blog ID
     * @param requestDTO DTO containing updated blog details
     * @param username   username of the blog owner
     * @return BlogDTO containing the updated blog details
     * @throws RuntimeException if blog is not found or user is not the owner
     */
    BlogDTO updateBlog(int id, BlogRequestDTO requestDTO, String username);

    /**
     * Delete a blog by ID.
     * Only the blog owner should be allowed to delete.
     *
     * @param id       blog ID
     * @param username username of the blog owner
     * @throws RuntimeException if blog is not found or user is not the owner
     */
    void deleteBlog(int id, String username);
}
