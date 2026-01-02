package com.example.BookManagement.service.book;

import com.example.BookManagement.dto.book.BookDTO;
import com.example.BookManagement.dto.book.BookPageResponse;
import com.example.BookManagement.dto.book.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.form.BookFilterForm;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/*
 * Service interface for managing books
 * Defines the operations that the Book service must implement
 */
public interface IBookService {

    // Get a paginated list of books based on filter criteria
    BookPageResponse getAllBooks(BookFilterForm form, Pageable pageable);

    // Find a book by its ID
    BookDTO getBookById(int id);

    // Create a new book
    BookDTO createBook(BookRequestDTO bookRequestDTO);

    // Update an existing book
    BookDTO updateBook(int id, BookRequestDTO bookRequestDTO);

    // Delete a book by its ID
    void deleteBook(int id);

    // Upload a book with upload to Cloudinary
    BookDTO createBookWithUpload(String title, String author, String category, String description,
                                MultipartFile image, MultipartFile pdf);
}
