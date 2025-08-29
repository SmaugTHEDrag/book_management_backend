package com.example.BookManagement.service.book;

import com.example.BookManagement.dto.book.BookDTO;
import com.example.BookManagement.dto.book.BookPageResponse;
import com.example.BookManagement.dto.book.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.form.BookFilterForm;
import org.springframework.data.domain.Pageable;

/*
 * Service interface for managing books.
 * Defines the operations that the Book service must implement.
 */
public interface IBookService {

    /**
     * Get a paginated list of books based on filter criteria
     *
     * @param form     Filter parameters (title, author, category, etc.)
     * @param pageable Pagination and sorting information
     * @return A page of books wrapped in a response object
     */
    BookPageResponse getAllBooks(BookFilterForm form, Pageable pageable);

    /**
     * Find a book by its ID
     *
     * @param id The ID of the book
     * @return The book entity if found
     */
    Book getBookById(int id);

    /**
     * Create a new book
     *
     * @param bookRequestDTO The data for creating a new book
     * @return The created book as a DTO
     */
    BookDTO createBook(BookRequestDTO bookRequestDTO);

    /**
     * Update an existing book
     *
     * @param id The ID of the book to update
     * @param bookRequestDTO The updated book data
     * @return The updated book as a DTO
     */
    BookDTO updateBook(int id, BookRequestDTO bookRequestDTO);

    /**
     * Delete a book by its ID
     *
     * @param id The ID of the book to delete
     */
    void deleteBook(int id);
}
