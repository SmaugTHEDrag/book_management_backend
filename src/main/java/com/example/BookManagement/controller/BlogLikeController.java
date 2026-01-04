package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogLikeDTO;
import com.example.BookManagement.service.blog.IBlogLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/blogs")
@Tag(name = "Blog Like API", description = "APIs for blog likes")
@RequiredArgsConstructor
public class BlogLikeController {

    private final IBlogLikeService likeService;

    @Operation(summary = "Get like count")
    @GetMapping("/{blogId}/likes/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Integer blogId) {
        return ResponseEntity.ok(likeService.getLikeCount(blogId));
    }

    @Operation(summary = "Check user like")
    @GetMapping("/{blogId}/likes/has")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(likeService.hasUserLiked(blogId, username));
    }

    @Operation(summary = "Like a blog")
    @PostMapping("/{blogId}/likes")
    public ResponseEntity<BlogLikeDTO> likeBlog(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        BlogLikeDTO dto = likeService.likeBlog(blogId, username);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Unlike a blog")
    @DeleteMapping("/{blogId}/likes")
    public ResponseEntity<Void> unlikeBlog(@PathVariable Integer blogId, Principal principal) {
        String username = principal.getName();
        likeService.unlikeBlog(blogId, username);
        return ResponseEntity.noContent().build();
    }

}
