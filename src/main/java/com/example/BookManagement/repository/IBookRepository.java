package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IBookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
}
