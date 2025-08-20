package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBlogCommentRepository extends JpaRepository<BlogComment, Integer> {
    List<BlogComment> findAllByBlogAndParentCommentIsNull(Blog blog);
    List<BlogComment> findAllByBlog(Blog blog);
}
