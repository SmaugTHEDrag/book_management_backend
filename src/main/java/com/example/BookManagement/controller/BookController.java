package com.example.BookManagement.controller;

import com.example.BookManagement.dto.book.BookDTO;
import com.example.BookManagement.dto.book.BookPageResponse;
import com.example.BookManagement.dto.book.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.form.BookFilterForm;
import com.example.BookManagement.service.file.FileUploadService;
import com.example.BookManagement.service.book.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.multipart.MultipartFile;

/*
 * REST Controller for Book Management.
 * Handles CRUD operations and retrieval of books.
 */
@RestController
@RequestMapping("api/books")
@Tag(name = "Book API", description = "APIs for managing books")
public class BookController {

    @Autowired
    private IBookService bookService;  // Service layer for book operations

    @Autowired
    private ModelMapper modelMapper;  // Maps entity objects to DTOs

    @Autowired
    private FileUploadService fileUploadService;

   // Get paginated books with optional filters
    @Operation(summary = "Get all books", description = "Retrieve paginated books with optional filters")
    @GetMapping
    public ResponseEntity<BookPageResponse> getAllBooks(BookFilterForm form, @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(bookService.getAllBooks(form,pageable));
    }

    // Get a single book by its ID
    @Operation(summary = "Get book by ID", description = "Retrieve a single book by its ID")
    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id){
        Book book = bookService.getBookById(id);
        BookDTO bookDTO = modelMapper.map(book,BookDTO.class);
        return ResponseEntity.ok(bookDTO);
    }

    // Create a new book (ADMIN only)
    @Operation(summary = "Create book", description = "Create a new book (Admin only)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookRequestDTO bookRequestDTO){
        BookDTO createBookDTO = bookService.createBook(bookRequestDTO);
        return new ResponseEntity<>(createBookDTO,HttpStatus.CREATED);
    }

    // Upload a new book integrate to Cloudinary (ADMIN only)
    @Operation(summary = "Upload book with files", description = "Upload a book with image and pdf to Cloudinary (Admin only)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<BookDTO> createBookWithUpload(
            // Text fields sent via multipart/form-data
            @RequestPart("title") String title,
            @RequestPart("author") String author,
            @RequestPart("category") String category,
            @RequestPart(value = "description", required = false) String description,
            // File uploads
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("pdf") MultipartFile pdf
    ) {
        try {
            // Upload PDF to Cloudinary folder "books/pdfs"
            String pdfUrl = fileUploadService.uploadFile(pdf, "books/pdfs");
            // Upload image (if provided) to Cloudinary folder "books/images"
            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                imageUrl = fileUploadService.uploadFile(image, "books/images");
            }
            // Build DTO with URLs from Cloudinary
            BookRequestDTO dto = new BookRequestDTO(
                    title,
                    author,
                    category,
                    imageUrl,
                    description,
                    pdfUrl
            );
            // Save book to DB through service
            BookDTO created = bookService.createBook(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update a book (ADMIN only)
    @Operation(summary = "Update book", description = "Update an existing book (Admin only)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody @Valid BookRequestDTO bookRequestDTO){
        BookDTO updateBookDTO = bookService.updateBook(id, bookRequestDTO);
        return ResponseEntity.ok(updateBookDTO);
    }

    // Delete a book (ADMIN only)
    @Operation(summary = "Delete book", description = "Delete a book by ID (Admin only)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
        return new ResponseEntity<>("Id: "+id+" is deleted", HttpStatus.OK);
    }
}
