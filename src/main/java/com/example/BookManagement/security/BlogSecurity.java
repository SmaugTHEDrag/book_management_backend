package com.example.BookManagement.security;

import com.example.BookManagement.repository.IBlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("blogSecurity")
@RequiredArgsConstructor
public class BlogSecurity {

    private final IBlogRepository blogRepository;

    // check if the given blog belongs to the current user
    public boolean isOwner(int blogId, String username) {
        return blogRepository.findById(blogId)
                .map(blog -> blog.getUser().getUsername().equals(username))
                .orElse(false);
    }
}
