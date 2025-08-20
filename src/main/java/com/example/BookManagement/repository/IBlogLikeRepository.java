package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogLike;
import com.example.BookManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBlogLikeRepository extends JpaRepository<BlogLike, Integer> {
    Optional<BlogLike> findByBlogAndUser(Blog blog, User user);
    boolean existsByBlogAndUser(Blog blog, User user);
    long countByBlog(Blog blog);
    List<BlogLike> findAllByBlog(Blog blog);
}
