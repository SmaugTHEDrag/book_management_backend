package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.entity.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBlogService {

    // Get all blogs
    List<BlogDTO> getAllBlogs();

    // Get blog by ID
    BlogDTO getBlogById(int id);

    // Create a new blog (no image)
    BlogDTO createBlog(BlogRequestDTO requestDTO, String username);

    // Update an existing blog
    BlogDTO updateBlog(int id, BlogRequestDTO requestDTO, String username);

    // Delete a blog by ID
    void deleteBlog(int id, String username);

    // Upload a blog with upload to Cloudinary
    BlogDTO createBlogWithUpload(String title, String content, MultipartFile image, String imageURL, String username);
}
