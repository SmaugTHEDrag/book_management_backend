package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBlogCommentRepository extends JpaRepository<BlogComment, Integer> {

    // Get all top-level comment (comment without parent comment) for a specific blog
    List<BlogComment> findAllByBlogAndParentCommentIsNull(Blog blog);

    // Get all comments (including replies) for a specific blog
    List<BlogComment> findAllByBlog(Blog blog);
}
