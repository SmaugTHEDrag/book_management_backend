package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.service.blog.IBlogService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ModelMapper modelMapper;

    // 🔹 Lấy tất cả blogs
    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    // 🔹 Lấy blog theo id
    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable int id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    // 🔹 Tạo blog mới
    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogRequestDTO blogRequestDTO,
                                              Principal principal) {
        BlogDTO blogDTO = blogService.createBlog(blogRequestDTO, principal.getName());
        return ResponseEntity.ok(blogDTO);
    }


    // 🔹 Cập nhật blog
    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable int id,
                                              @RequestBody BlogRequestDTO blogRequestDTO,
                                              Principal principal) {
        BlogDTO blogDTO = blogService.updateBlog(id, blogRequestDTO, principal.getName());
        return ResponseEntity.ok(blogDTO);
    }

    // 🔹 Xoá blog
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable int id,
                                           Principal principal) {
        blogService.deleteBlog(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}