package com.example.BookManagement.common.security;

import com.example.BookManagement.blog.repository.IBlogCommentRepository;
import com.example.BookManagement.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("commentSecurity")
@RequiredArgsConstructor
public class CommentSecurity {

    private final IBlogCommentRepository commentRepository;

    private final IUserRepository userRepository;

    // comment owner or blog owner can delete the comment
    public boolean canDelete(Integer commentId, String username) {
        if (commentId == null || username == null) return false;

        return commentRepository.findById(commentId)
                .map(comment ->
                        comment.getUser().getUsername().equals(username) || // Comment owner
                                comment.getBlog().getUser().getUsername().equals(username) // Blog owner
                )
                .orElse(false);
    }

    // only comment owner can edit
    public boolean canEdit(Integer commentId, String username) {
        if (commentId == null || username == null) return false;

        return commentRepository.findById(commentId)
                .map(comment -> comment.getUser().getUsername().equals(username)) // Only comment owner
                .orElse(false);
    }
}

