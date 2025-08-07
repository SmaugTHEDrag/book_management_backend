package com.example.BookManagement.service;

import com.example.BookManagement.dto.BookDTO;
import com.example.BookManagement.dto.BookRequestDTO;
import com.example.BookManagement.entity.Book;

import java.util.List;

public interface IBookService {
    List<Book> getAllBooks();

    Book getBookById(int id);

    BookDTO createBook(BookRequestDTO bookRequestDTO);

    BookDTO updateBook(int id, BookRequestDTO bookRequestDTO);

    void deleteBook(int id);
}
