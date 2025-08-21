package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

/*
* Repository interface for Blog entity
* Provide basic CRUD operations for managing blogs
*
* JpaRepository gives basic CRUD methods (save, findById, findAll, delete, etc.).
 */
public interface IBlogRepository extends JpaRepository<Blog, Integer> {
}
