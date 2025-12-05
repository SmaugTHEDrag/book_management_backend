package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.service.blog.IBlogService;
import com.example.BookManagement.service.file.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/*
 * REST Controller for managing Blog resources.
 * Provides endpoints to create, read, update, and delete blogs.
 * Requires authentication for creating, updating, and deleting blogs.
 */
@RestController
@RequestMapping("/api/blogs")
@Tag(name = "Blog API", description = "APIs for managing blogs")
public class BlogController {

    @Autowired
    private IBlogService blogService;  // Service layer handling blog business logic

    @Autowired
    private ModelMapper modelMapper;  // Used to map entities to DTOs

    // Get all blogs
    @Operation(summary = "Get all blogs", description = "Retrieve a list of all blogs")
    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    // Get blog by ID
    @Operation(summary = "Get blog by ID", description = "Retrieve details of a single blog by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable int id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @Operation(summary = "Create a blog", description = "Authenticated user can create a new blog without image")
    // Create blog (authenticated user)
    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogRequestDTO blogRequestDTO, Principal principal) {
        BlogDTO blogDTO = blogService.createBlog(blogRequestDTO, principal.getName());
        return ResponseEntity.ok(blogDTO);
    }

    // Upload a blog image integrate to Cloudinary
    @Operation(summary = "Create blog with image", description = "Upload a blog image to Cloudinary or provide image URL")
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<BlogDTO> createBlogWithUpload(
            // Text fields sent via multipart/form-data
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            // File uploads
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "imageURL", required = false) String imageURL,
            Principal principal
    ) {
        BlogDTO created = blogService.createBlogWithUpload(title, content, image, imageURL, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Update blog (BLog owner only)
    @Operation(summary = "Update a blog", description = "Update blog details (only owner can update)")
    @PreAuthorize("@blogSecurity.isOwner(#id, authentication.name)")
    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable int id, @RequestBody BlogRequestDTO blogRequestDTO, Principal principal) {
        BlogDTO blogDTO = blogService.updateBlog(id, blogRequestDTO, principal.getName());
        return ResponseEntity.ok(blogDTO);
    }

    // Delete blog (Blog owner or admin only)
    @Operation(summary = "Delete a blog", description = "Delete a blog (admin or owner)")
    @PreAuthorize("hasAuthority('ADMIN') or @blogSecurity.isOwner(#id, authentication.name)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable int id, Principal principal) {
        blogService.deleteBlog(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}