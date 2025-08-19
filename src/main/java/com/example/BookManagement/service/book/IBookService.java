package com.example.BookManagement.service.book;

import com.example.BookManagement.dto.book.BookDTO;
import com.example.BookManagement.dto.book.BookPageResponse;
import com.example.BookManagement.dto.book.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.form.BookFilterForm;
import org.springframework.data.domain.Pageable;

public interface IBookService {
    BookPageResponse getAllBooks(BookFilterForm form, Pageable pageable);

    Book getBookById(int id);

    BookDTO createBook(BookRequestDTO bookRequestDTO);

    BookDTO updateBook(int id, BookRequestDTO bookRequestDTO);

    void deleteBook(int id);
}
