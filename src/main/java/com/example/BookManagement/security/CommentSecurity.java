package com.example.BookManagement.security;

import com.example.BookManagement.entity.User;
import com.example.BookManagement.repository.IBlogCommentRepository;
import com.example.BookManagement.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
 * Component to handle permissions for BlogComment operations.
 * Can be used with Spring Security @PreAuthorize
 * @PreAuthorize("@commentSecurity.canDelete(#commentId, authentication.name)")
 */
@Component("commentSecurity")
public class CommentSecurity {

    @Autowired
    private IBlogCommentRepository commentRepository;

    @Autowired
    private IUserRepository userRepository;

    /**
     * Checks if a user can delete a comment.
     * Rules:
     * The comment owner can delete their own comment.
     * The blog owner (of the blog containing the comment) can delete any comment on their blog.
     * Otherwise, deletion is not allowed.
     *
     * @param commentId the ID of the comment
     * @param username the username of the current user
     * @return true if deletion is allowed, false otherwise
     */
    public boolean canDelete(Integer commentId, String username) {
        if (commentId == null || username == null) {
            return false;
        }

        return commentRepository.findById(commentId)
                .map(comment -> {
                    // Get the current user to check their role
                    Optional<User> currentUser = userRepository.findByUsername(username);

                    // Comment owner can delete their own comment
                    String commentOwner = comment.getUser().getUsername();
                    if (commentOwner.equals(username)) {
                        return true;
                    }

                    // Blog owner can delete any comment on their blog
                    String blogOwner = comment.getBlog().getUser().getUsername();
                    if (blogOwner.equals(username)) {
                        return true;
                    }
                    // Otherwise, user cannot delete this comment
                    return false;
                })
                .orElse(false);
    }

    /**
     * Checks if a user can edit a comment.
     * Rules:
     * Only the comment owner can edit their comment.
     * Blog owner cannot edit others' comments.
     *
     * @param commentId the ID of the comment
     * @param username the username of the current user
     * @return true if editing is allowed, false otherwise
     */
    public boolean canEdit(Integer commentId, String username) {
        if (commentId == null || username == null) {
            return false;
        }

        return commentRepository.findById(commentId)
                .map(comment -> {
                    // Get the current user to check their role
                    Optional<User> currentUser = userRepository.findByUsername(username);

                    // Only comment owner can edit their comment (not blog owner)
                    String commentOwner = comment.getUser().getUsername();
                    return commentOwner.equals(username);
                })
                .orElse(false);
    }
}

