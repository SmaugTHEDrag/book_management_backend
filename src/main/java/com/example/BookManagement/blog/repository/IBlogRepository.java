package com.example.BookManagement.blog.repository;

import com.example.BookManagement.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlogRepository extends JpaRepository<Blog, Integer> {
}
