package com.example.BookManagement.blog.service;

import com.example.BookManagement.blog.dto.BlogDTO;
import com.example.BookManagement.blog.dto.BlogRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBlogService {

    List<BlogDTO> getAllBlogs();

    BlogDTO getBlogById(int id);

    BlogDTO createBlog(BlogRequestDTO requestDTO, String username);

    // only blog owner can update
    BlogDTO updateBlog(int id, BlogRequestDTO requestDTO, String username);

    // blog owner and admin check before delete
    void deleteBlog(int id, String username);

    // create blog and upload image if provided
    BlogDTO createBlogWithUpload(String title, String content, MultipartFile image, String imageURL, String username);
}
