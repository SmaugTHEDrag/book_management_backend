package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogLike;
import com.example.BookManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBlogLikeRepository extends JpaRepository<BlogLike, Integer> {

    // Find a like on blog and by user
    Optional<BlogLike> findByBlogAndUser(Blog blog, User user);

    // Check if a specific user has liked a specific blog
    boolean existsByBlogAndUser(Blog blog, User user);

    // Count the total number of likes for a specific blog
    long countByBlog(Blog blog);

    // Get all likes for a specific blog
    List<BlogLike> findAllByBlog(Blog blog);
}
