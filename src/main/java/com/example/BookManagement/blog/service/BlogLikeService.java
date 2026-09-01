package com.example.BookManagement.blog.service;

import com.example.BookManagement.blog.dto.BlogLikeDTO;
import com.example.BookManagement.blog.entity.Blog;
import com.example.BookManagement.blog.entity.BlogLike;
import com.example.BookManagement.user.entity.User;
import com.example.BookManagement.blog.mapper.BlogLikeMapper;
import com.example.BookManagement.blog.repository.IBlogLikeRepository;
import com.example.BookManagement.blog.repository.IBlogRepository;
import com.example.BookManagement.user.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BlogLikeService implements IBlogLikeService{

    private final IBlogLikeRepository likeRepository;

    private final IBlogRepository blogRepository;

    private final IUserRepository userRepository;

    private final BlogLikeMapper blogLikeMapper;

    @Override
    public BlogLikeDTO likeBlog(Integer blogId, String username) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<BlogLike> existing = likeRepository.findByBlogAndUser(blog, user);
        if (existing.isPresent()) {
            return blogLikeMapper.toDTO(existing.get());
        }

        BlogLike like = new BlogLike();
        like.setBlog(blog);
        like.setUser(user);

        BlogLike saved = likeRepository.save(like);
        return blogLikeMapper.toDTO(saved);
    }


    @Override
    public void unlikeBlog(Integer blogId, String username) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BlogLike like = likeRepository.findByBlogAndUser(blog, user)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        likeRepository.delete(like);
    }

    @Override
    public long getLikeCount(Integer blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        return likeRepository.countByBlog(blog);
    }

    @Override
    public boolean hasUserLiked(Integer blogId, String username) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return likeRepository.existsByBlogAndUser(blog, user);
    }

}
