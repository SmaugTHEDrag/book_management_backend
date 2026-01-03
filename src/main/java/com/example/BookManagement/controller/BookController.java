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
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book API", description = "APIs for managing books")
@RequiredArgsConstructor
public class BookController {

    private final IBookService bookService;  // Service layer for book operations

    // get paginated books with optional filters
    @Operation(summary = "Get all books")
    @GetMapping
    public ResponseEntity<BookPageResponse> getAllBooks(BookFilterForm form, @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(bookService.getAllBooks(form,pageable));
    }

    // get a book by ID
    @Operation(summary = "Get book by ID")
    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // create a new book (ADMIN only) (no image)
    @Operation(summary = "Create book")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookRequestDTO bookRequestDTO){
        BookDTO createBookDTO = bookService.createBook(bookRequestDTO);
        return new ResponseEntity<>(createBookDTO,HttpStatus.CREATED);
    }

    // create book with image/file (Cloudinary)
    @Operation(summary = "Upload book with files")
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
        BookDTO dto = bookService.createBookWithUpload(title, author, category, description, image, pdf);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // update a book (ADMIN only)
    @Operation(summary = "Update book", description = "Update an existing book (Admin only)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody @Valid BookRequestDTO bookRequestDTO){
        BookDTO updateBookDTO = bookService.updateBook(id, bookRequestDTO);
        return ResponseEntity.ok(updateBookDTO);
    }

    // delete a book (ADMIN only)
    @Operation(summary = "Delete book", description = "Delete a book by ID (Admin only)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
        return new ResponseEntity<>("Id: "+id+" is deleted", HttpStatus.OK);
    }
}
