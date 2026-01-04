package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;
import com.example.BookManagement.service.blog.IBlogCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@Tag(name = "Blog Comment API", description = "APIs for blog comments")
@RequiredArgsConstructor
public class BlogCommentController {

    private final IBlogCommentService commentService;

    @Operation(summary = "Get comments for a blog")
    @GetMapping("/{blogId}/comments")
    public ResponseEntity<List<BlogCommentDTO>> getComments(@PathVariable Integer blogId) {
        return ResponseEntity.ok(commentService.getCommentsByBlog(blogId));
    }

    @Operation(summary = "Add a comment")
    @PostMapping("/{blogId}/comments")
    public ResponseEntity<BlogCommentDTO> addComment(@RequestBody @Valid BlogCommentRequestDTO request, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(commentService.addComment(request, username));
    }

    @Operation(summary = "Update comment", description = "Only Comment owner can update a comment")
    @PreAuthorize("@commentSecurity.canEdit(#commentId, authentication.name)")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<BlogCommentDTO> updateComment(@PathVariable Integer commentId, @RequestBody @Valid BlogCommentRequestDTO request, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(commentService.updateComment(commentId, request, username));
    }

    @Operation(summary = "Delete comment", description = "Only Blog owner, Admin and Comment owner can delete a comment")
    @PreAuthorize("hasAuthority('ADMIN') or @commentSecurity.canDelete(#commentId, authentication.name)")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId, Principal principal) {
        String username = principal.getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.noContent().build();
    }
}
