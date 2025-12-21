package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogLikeDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogLike;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.repository.IBlogLikeRepository;
import com.example.BookManagement.repository.IBlogRepository;
import com.example.BookManagement.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * Service implementation for managing blog likes.
 * Handles logic for liking, unliking, and retrieving like information for blogs.
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BlogLikeService implements IBlogLikeService{

    private final IBlogLikeRepository likeRepository;

    private final IBlogRepository blogRepository;

    private final IUserRepository userRepository;

    private final ModelMapper modelMapper;

    // Like a blog for the given user (idempotent: if the user already liked the blog, returns existing like)
    @Override
    public BlogLikeDTO likeBlog(Integer blogId, String username) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // If already liked → return existing
        Optional<BlogLike> existing = likeRepository.findByBlogAndUser(blog, user);
        if (existing.isPresent()) {
            return modelMapper.map(existing.get(), BlogLikeDTO.class);
        }

        BlogLike like = new BlogLike();
        like.setBlog(blog);
        like.setUser(user);

        BlogLike saved = likeRepository.save(like);
        return modelMapper.map(saved, BlogLikeDTO.class);
    }


    // Remove a user's like from a blog
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

    // Get the total number of likes for a specific blog
    @Override
    public long getLikeCount(Integer blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        return likeRepository.countByBlog(blog);
    }

    // Check if a specific user has liked a specific blog
    @Override
    public boolean hasUserLiked(Integer blogId, String username) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return likeRepository.existsByBlogAndUser(blog, user);
    }

    // Map a BlogLike entity to BlogLikeDTO
    private BlogLikeDTO mapToDTO(BlogLike like) {
        BlogLikeDTO dto = new BlogLikeDTO();
        dto.setId(like.getId());
        dto.setBlogId(like.getBlog() != null ? like.getBlog().getId() : null);
        dto.setUsername(like.getUser() != null ? like.getUser().getUsername() : null);
        dto.setLikedAt(like.getLikedAt());
        return dto;
    }
}
