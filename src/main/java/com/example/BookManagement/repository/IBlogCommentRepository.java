package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBlogCommentRepository extends JpaRepository<BlogComment, Integer> {

    // top-level comments of a blog (no replies)
    List<BlogComment> findAllByBlogAndParentCommentIsNull(Blog blog);

    // all comments of a blog (have replies)
    List<BlogComment> findAllByBlog(Blog blog);
}
