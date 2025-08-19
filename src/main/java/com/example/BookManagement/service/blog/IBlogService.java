package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.entity.Blog;

import java.util.List;

public interface IBlogService {
    List<BlogDTO> getAllBlogs();

    BlogDTO getBlogById(int id);

    BlogDTO createBlog(BlogRequestDTO requestDTO, String username);

    BlogDTO updateBlog(int id, BlogRequestDTO requestDTO, String username);

    void deleteBlog(int id, String username);
}
