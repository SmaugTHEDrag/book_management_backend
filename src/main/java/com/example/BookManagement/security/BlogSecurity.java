package com.example.BookManagement.security;

import com.example.BookManagement.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("blogSecurity")
public class BlogSecurity {

    @Autowired
    private IBlogRepository blogRepository;

    public boolean isOwner(int blogId, String username) {
        return blogRepository.findById(blogId)
                .map(blog -> blog.getUser().getUsername().equals(username))
                .orElse(false);
    }
}
