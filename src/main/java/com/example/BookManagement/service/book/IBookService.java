package com.example.BookManagement.service.book;

import com.example.BookManagement.dto.book.BookDTO;
import com.example.BookManagement.dto.book.BookPageResponse;
import com.example.BookManagement.dto.book.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.form.BookFilterForm;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface IBookService {

    BookPageResponse getAllBooks(BookFilterForm form, Pageable pageable);

    BookDTO getBookById(int id);

    BookDTO createBook(BookRequestDTO bookRequestDTO);

    BookDTO updateBook(int id, BookRequestDTO bookRequestDTO);

    void deleteBook(int id);

    // create book with image + pdf upload (cloud storage)
    BookDTO createBookWithUpload(String title, String author, String category, String description,
                                MultipartFile image, MultipartFile pdf);
}
