package com.example.BookManagement.book.repository;

import com.example.BookManagement.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IBookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
}
