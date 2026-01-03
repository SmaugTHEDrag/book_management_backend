package com.example.BookManagement.security;

import com.example.BookManagement.entity.User;
import com.example.BookManagement.repository.IBlogCommentRepository;
import com.example.BookManagement.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
 * Component to handle permissions for BlogComment operations
 *  Called inside @PreAuthorize in controllers
 * @PreAuthorize("@commentSecurity.canDelete(#commentId, authentication.name)")
 */
@Component("commentSecurity")
@RequiredArgsConstructor
public class CommentSecurity {

    private final IBlogCommentRepository commentRepository;

    private final IUserRepository userRepository;

    // Comment owner or blog owner can delete the comment
    public boolean canDelete(Integer commentId, String username) {
        if (commentId == null || username == null) return false;

        return commentRepository.findById(commentId)
                .map(comment ->
                        comment.getUser().getUsername().equals(username) || // Comment owner
                                comment.getBlog().getUser().getUsername().equals(username) // Blog owner
                )
                .orElse(false);
    }

    // Only comment owner can edit
    public boolean canEdit(Integer commentId, String username) {
        if (commentId == null || username == null) return false;

        return commentRepository.findById(commentId)
                .map(comment -> comment.getUser().getUsername().equals(username)) // Only comment owner
                .orElse(false);
    }
}

