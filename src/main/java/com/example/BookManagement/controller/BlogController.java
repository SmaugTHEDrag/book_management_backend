package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.service.blog.IBlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@Tag(name = "Blog API", description = "APIs for blogs")
@RequiredArgsConstructor
public class BlogController {

    private final IBlogService blogService;

    @Operation(summary = "Get all blogs")
    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @Operation(summary = "Get blog by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable int id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @Operation(summary = "Create a blog")
    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogRequestDTO blogRequestDTO, Principal principal) {
        BlogDTO blogDTO = blogService.createBlog(blogRequestDTO, principal.getName());
        return ResponseEntity.ok(blogDTO);
    }

    @Operation(summary = "Create blog with image")
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<BlogDTO> createBlogWithUpload(
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "imageURL", required = false) String imageURL,
            Principal principal
    ) {
        BlogDTO created = blogService.createBlogWithUpload(title, content, image, imageURL, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update a blog", description = "Only Blog owner can update a blog")
    @PreAuthorize("@blogSecurity.isOwner(#id, authentication.name)")
    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable int id, @RequestBody @Valid BlogRequestDTO blogRequestDTO, Principal principal) {
        BlogDTO blogDTO = blogService.updateBlog(id, blogRequestDTO, principal.getName());
        return ResponseEntity.ok(blogDTO);
    }

    @Operation(summary = "Delete a blog", description = "Only Admin and Blog owner can delete a blog")
    @PreAuthorize("hasAuthority('ADMIN') or @blogSecurity.isOwner(#id, authentication.name)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable int id, Principal principal) {
        blogService.deleteBlog(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}