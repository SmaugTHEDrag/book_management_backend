package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*
 * Repository interface for Book entity
 */
public interface IBookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
}
