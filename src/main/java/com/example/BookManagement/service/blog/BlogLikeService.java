package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogLikeDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogLike;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.mapper.BlogLikeMapper;
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

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BlogLikeService implements IBlogLikeService{

    private final IBlogLikeRepository likeRepository;

    private final IBlogRepository blogRepository;

    private final IUserRepository userRepository;

    private final BlogLikeMapper blogLikeMapper;

    // Like blog (return existing like if already liked)
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


    // Unlike blog
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

    // Count likes of a blog
    @Override
    public long getLikeCount(Integer blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        return likeRepository.countByBlog(blog);
    }

    // Check if user liked blog
    @Override
    public boolean hasUserLiked(Integer blogId, String username) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return likeRepository.existsByBlogAndUser(blog, user);
    }

}
