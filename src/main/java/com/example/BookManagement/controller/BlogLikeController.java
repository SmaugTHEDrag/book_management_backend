package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogLikeDTO;
import com.example.BookManagement.service.blog.IBlogLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/blogs")
public class BlogLikeController {
    @Autowired
    private IBlogLikeService likeService;

    // like (idempotent)
    @PostMapping("/{blogId}/likes")
    public ResponseEntity<BlogLikeDTO> likeBlog(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        BlogLikeDTO dto = likeService.likeBlog(blogId, username);
        return ResponseEntity.ok(dto);
    }

    // unlike
    @DeleteMapping("/{blogId}/likes")
    public ResponseEntity<Void> unlikeBlog(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        likeService.unlikeBlog(blogId, username);
        return ResponseEntity.noContent().build();
    }

    // count
    @GetMapping("/{blogId}/likes/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Integer blogId) {
        return ResponseEntity.ok(likeService.getLikeCount(blogId));
    }

    // check if user liked
    @GetMapping("/{blogId}/likes/has")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(likeService.hasUserLiked(blogId, username));
    }
}
