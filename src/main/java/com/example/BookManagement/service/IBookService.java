package com.example.BookManagement.service;

import com.example.BookManagement.dto.BookDTO;
import com.example.BookManagement.dto.BookPageResponse;
import com.example.BookManagement.dto.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.form.BookFilterForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookService {
    BookPageResponse getAllBooks(BookFilterForm form, Pageable pageable);

    Book getBookById(int id);

    BookDTO createBook(BookRequestDTO bookRequestDTO);

    BookDTO updateBook(int id, BookRequestDTO bookRequestDTO);

    void deleteBook(int id);
}
