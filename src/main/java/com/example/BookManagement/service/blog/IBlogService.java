package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.entity.Blog;
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
