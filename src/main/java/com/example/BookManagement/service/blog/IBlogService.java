package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.entity.Blog;

import java.util.List;

/*
 * Service interface for managing blogs
 * Defines methods for creating, updating, retrieving, and deleting blogs
 */
public interface IBlogService {

    // Get a list of all blogs
    List<BlogDTO> getAllBlogs();

    // Get details of a specific blog by ID
    BlogDTO getBlogById(int id);

    // Create a new blog for the given user
    BlogDTO createBlog(BlogRequestDTO requestDTO, String username);


    // Update an existing blog
    BlogDTO updateBlog(int id, BlogRequestDTO requestDTO, String username);

    // Delete a blog by ID
    void deleteBlog(int id, String username);
}
