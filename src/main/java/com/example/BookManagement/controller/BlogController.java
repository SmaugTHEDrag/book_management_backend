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

    /**
     * GET /api/blogs
     * Retrieve a list of all blogs.
     * Public endpoint (no authentication required if security permits).
     *
     * @return List of BlogDTO representing all blogs.
     */
    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    /**
     * GET /api/blogs/{id}
     * Retrieve a specific blog by its ID.
     *
     * @param id the ID of the blog
     * @return BlogDTO representing the blog
     */
    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable int id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    /**
     * POST /api/blogs
     * Create a new blog.
     * The authenticated user's username is passed via Principal.
     *
     * @param blogRequestDTO the request body containing blog details
     * @param principal the authenticated user
     * @return BlogDTO representing the created blog
     */
    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogRequestDTO blogRequestDTO,
                                              Principal principal) {
        BlogDTO blogDTO = blogService.createBlog(blogRequestDTO, principal.getName());
        return ResponseEntity.ok(blogDTO);
    }

    /**
     * PUT /api/blogs/{id}
     * Update an existing blog.
     * Only the owner or authorized user can update the blog.
     *
     * @param id the ID of the blog to update
     * @param blogRequestDTO the updated blog data
     * @param principal the authenticated user
     * @return BlogDTO representing the updated blog
     */
    @PreAuthorize("hasAuthority('ADMIN') or @blogSecurity.isOwner(#id, principal.name)")
    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable int id,
                                              @RequestBody BlogRequestDTO blogRequestDTO,
                                              Principal principal) {
        BlogDTO blogDTO = blogService.updateBlog(id, blogRequestDTO, principal.getName());
        return ResponseEntity.ok(blogDTO);
    }

    /**
     * DELETE /api/blogs/{id}
     * Delete an existing blog.
     * Only the owner or authorized user can delete the blog.
     *
     * @param id the ID of the blog to delete
     * @param principal the authenticated user
     * @return ResponseEntity with no content
     */
    @PreAuthorize("hasAuthority('ADMIN') or @blogSecurity.isOwner(#id, principal.name)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable int id,
                                           Principal principal) {
        blogService.deleteBlog(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}