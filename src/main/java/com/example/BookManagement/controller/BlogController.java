package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.service.blog.IBlogService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*
 * REST Controller for managing Blog resources.
 * Provides endpoints to create, read, update, and delete blogs.
 * Requires authentication for creating, updating, and deleting blogs.
 */
@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private IBlogService blogService;  // Service layer handling blog business logic

    @Autowired
    private ModelMapper modelMapper;  // Used to map entities to DTOs

    // Get all blogs
    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    // Get blog by ID
    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable int id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    // Create blog (authenticated user)
    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogRequestDTO blogRequestDTO,
                                              Principal principal) {
        BlogDTO blogDTO = blogService.createBlog(blogRequestDTO, principal.getName());
        return ResponseEntity.ok(blogDTO);
    }

    // Update blog (BLog owner only)
    @PreAuthorize("@blogSecurity.isOwner(#id, authentication.name)")
    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable int id,
                                              @RequestBody BlogRequestDTO blogRequestDTO,
                                              Principal principal) {
        BlogDTO blogDTO = blogService.updateBlog(id, blogRequestDTO, principal.getName());
        return ResponseEntity.ok(blogDTO);
    }

    // Delete blog (Blog owner or admin only)
    @PreAuthorize("hasAuthority('ADMIN') or @blogSecurity.isOwner(#id, authentication.name)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable int id,
                                           Principal principal) {
        blogService.deleteBlog(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}