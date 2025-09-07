package com.example.BookManagement.security;

import com.example.BookManagement.entity.User;
import com.example.BookManagement.repository.IBlogCommentRepository;
import com.example.BookManagement.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
 * Component to handle permissions for BlogComment operations
 *  Called inside @PreAuthorize in controllers
 * @PreAuthorize("@commentSecurity.canDelete(#commentId, authentication.name)")
 */
@Component("commentSecurity")
public class CommentSecurity {

    @Autowired
    private IBlogCommentRepository commentRepository;

    @Autowired
    private IUserRepository userRepository;


    // Check if the user can delete the blog comment
    // Comment owner -> can delete
    // Blog owner -> can delete
    public boolean canDelete(Integer commentId, String username) {
        if (commentId == null || username == null) return false;

        return commentRepository.findById(commentId)
                .map(comment ->
                        comment.getUser().getUsername().equals(username) || // Comment owner
                                comment.getBlog().getUser().getUsername().equals(username) // Blog owner
                )
                .orElse(false);
    }

    // Check if the user can edit the comment
    // Only comment owner can edit
    public boolean canEdit(Integer commentId, String username) {
        if (commentId == null || username == null) return false;

        return commentRepository.findById(commentId)
                .map(comment -> comment.getUser().getUsername().equals(username)) // Only comment owner
                .orElse(false);
    }
}

