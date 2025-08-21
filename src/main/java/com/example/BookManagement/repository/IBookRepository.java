package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*
* Repository interface for Book entity
* Provide CRUD operations and queries using JPA specification
*
* JpaRepository gives basic CRUD methods (save, findById, findAll, delete, etc.).
* JpaSpecificationExecutor allows building dynamic queries with specifications.
 */
public interface IBookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
}
