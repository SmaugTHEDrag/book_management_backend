package com.example.BookManagement.security;

import com.example.BookManagement.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * Component to handle permission checks for Blog operations.
 * Can be used with Spring Security @PreAuthorize, for example:
 * @PreAuthorize("@blogSecurity.isOwner(#blogId, authentication.name)")
 */
@Component("blogSecurity")
public class BlogSecurity {

    @Autowired
    private IBlogRepository blogRepository;

    /**
     * Checks if the current user is the owner of the blog.
     *
     * @param blogId the ID of the blog
     * @param username the username of the current user
     * @return true if the current user is the blog owner, false otherwise
     */
    public boolean isOwner(int blogId, String username) {
        return blogRepository.findById(blogId)
                // Compare blog owner's username with current user
                .map(blog -> blog.getUser().getUsername().equals(username))
                .orElse(false);
    }
}
