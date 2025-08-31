package com.example.BookManagement.security;

import com.example.BookManagement.entity.User;
import com.example.BookManagement.repository.IBlogCommentRepository;
import com.example.BookManagement.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("commentSecurity")
public class CommentSecurity {

    @Autowired
    private IBlogCommentRepository commentRepository;

    @Autowired
    private IUserRepository userRepository;

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

                    return false;
                })
                .orElse(false);
    }

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

