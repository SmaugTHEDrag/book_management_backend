package com.example.BookManagement.controller;

import com.example.BookManagement.dto.book.BookDTO;
import com.example.BookManagement.dto.book.BookPageResponse;
import com.example.BookManagement.dto.book.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.form.BookFilterForm;
import com.example.BookManagement.service.book.IBookService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

/*
 * REST Controller for Book Management.
 * Handles CRUD operations and retrieval of books.
 */
@RestController
@RequestMapping("api/books")
public class BookController {
    @Autowired
    private IBookService bookService;  // Service layer for book operations

    @Autowired
    private ModelMapper modelMapper;  // Maps entity objects to DTOs

    /**
     * GET /api/books
     * Get a paginated list of books with optional filters.
     * Accessible by all users.
     *
     * @param form filter form (title, author, genre, etc.)
     * @param pageable pagination and sorting info
     * @return paginated list of books
     */
    @GetMapping
    public ResponseEntity<BookPageResponse> getAllBooks(BookFilterForm form, @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(bookService.getAllBooks(form,pageable));
    }

    /**
     * GET /api/books/{id}
     * Get a single book by its ID.
     * Accessible by all users.
     *
     * @param id book ID
     * @return book data mapped to BookDTO
     */
    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id){
        Book book = bookService.getBookById(id);
        BookDTO bookDTO = modelMapper.map(book,BookDTO.class);
        return ResponseEntity.ok(bookDTO);
    }

    /**
     * POST /api/books
     * Create a new book.
     * Only ADMIN users can create books.
     *
     * @param bookRequestDTO DTO containing book info
     * @return created book data with HTTP 201 status
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookRequestDTO bookRequestDTO){
        BookDTO createBookDTO = bookService.createBook(bookRequestDTO);
        return new ResponseEntity<>(createBookDTO,HttpStatus.CREATED);
    }

    /**
     * PUT /api/books
     * Update an existing book by ID.
     * Only ADMIN users can update books.
     *
     * @param id book ID
     * @param bookRequestDTO DTO containing updated fields
     * @return updated book data
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody @Valid BookRequestDTO bookRequestDTO){
        BookDTO updateBookDTO = bookService.updateBook(id, bookRequestDTO);
        return ResponseEntity.ok(updateBookDTO);
    }

    /**
     * DELETE /api/books/{id}
     * Delete a book by ID.
     * Only ADMIN users can delete books.
     *
     * @param id book ID
     * @return confirmation message
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
        return new ResponseEntity<>("Id: "+id+" is deleted", HttpStatus.OK);
    }
}
