package com.example.BookManagement.blog.service;

import com.example.BookManagement.blog.dto.BlogLikeDTO;
import com.example.BookManagement.blog.entity.Blog;
import com.example.BookManagement.blog.entity.BlogLike;
import com.example.BookManagement.user.entity.User;
import com.example.BookManagement.common.exception.ResourceNotFoundException;
import com.example.BookManagement.common.security.CurrentUserService;
import com.example.BookManagement.blog.mapper.BlogLikeMapper;
import com.example.BookManagement.blog.repository.IBlogLikeRepository;
import com.example.BookManagement.blog.repository.IBlogRepository;
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

    private final CurrentUserService currentUserService;

    private final BlogLikeMapper blogLikeMapper;

    @Override
    public BlogLikeDTO likeBlog(Integer blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));

        User user = currentUserService.getCurrentUser();

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
    public void unlikeBlog(Integer blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));

        User user = currentUserService.getCurrentUser();

        BlogLike like = likeRepository.findByBlogAndUser(blog, user)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        likeRepository.delete(like);
    }

    @Override
    public long getLikeCount(Integer blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
        return likeRepository.countByBlog(blog);
    }

    @Override
    public boolean hasUserLiked(Integer blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
        return likeRepository.existsByBlogAndUser(blog, currentUserService.getCurrentUser());
    }

}
