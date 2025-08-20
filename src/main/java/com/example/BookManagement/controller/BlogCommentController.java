package com.example.BookManagement.controller;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;
import com.example.BookManagement.service.blog.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogCommentController {

    @Autowired
    private IBlogCommentService commentService;

    // thêm comment hoặc reply
    @PostMapping("/{blogId}/comments")
    public ResponseEntity<BlogCommentDTO> addComment(@PathVariable Integer blogId, @RequestBody BlogCommentRequestDTO request,
                                                     Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(commentService.addComment(blogId, request, username));
    }

    // update comment
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<BlogCommentDTO> updateComment(@PathVariable Integer commentId,
                                                        @RequestBody BlogCommentRequestDTO request,
                                                        Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(commentService.updateComment(commentId, request, username));
    }

    // delete comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId,
                                              Principal principal) {
        String username = principal.getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.noContent().build();
    }

    // get comments cho blog (top-level + nested replies)
    @GetMapping("/{blogId}/comments")
    public ResponseEntity<List<BlogCommentDTO>> getComments(@PathVariable Integer blogId) {
        return ResponseEntity.ok(commentService.getCommentsByBlog(blogId));
    }
}
