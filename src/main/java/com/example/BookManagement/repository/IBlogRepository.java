package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repository interface for Blog entity
 */
public interface IBlogRepository extends JpaRepository<Blog, Integer> {
}
