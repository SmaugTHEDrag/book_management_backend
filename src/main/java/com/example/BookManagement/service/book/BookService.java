package com.example.BookManagement.service.book;

import com.example.BookManagement.dto.book.BookDTO;
import com.example.BookManagement.dto.book.BookPageResponse;
import com.example.BookManagement.dto.book.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.exception.ResourceNotFoundException;
import com.example.BookManagement.form.BookFilterForm;
import com.example.BookManagement.repository.IBookRepository;
import com.example.BookManagement.specification.BookSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/*
 * Service implementation for managing books
 * Handles business logic and interacts with the repository
 */
@Service
public class BookService implements IBookService {
    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Retrieves a paginated list of books based on filter criteria.
    @Override
    public BookPageResponse getAllBooks(BookFilterForm form, Pageable pageable) {
        // Build search/filter conditions
        Specification<Book> where = BookSpecification.buildWhere(form);

        // Get paginated books from database
        Page<Book> books = bookRepository.findAll(where, pageable);

        // Convert Book entities to BookDTOs
        Page<BookDTO> bookDTOs = books.map(book -> modelMapper.map(book, BookDTO.class));

        // Return a page response with metadata
        return new BookPageResponse(
            bookDTOs.getContent(),
            bookDTOs.getNumber(),
            bookDTOs.getTotalElements(),
            bookDTOs.getTotalPages(),
            bookDTOs.getSize(),
            bookDTOs.isLast(),
            bookDTOs.isFirst()
        );
    }

    // Retrieves a book by its ID
    @Override
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }


    // Creates a new book entry
    @Override
    public BookDTO createBook(BookRequestDTO bookRequestDTO) {
        // Map request DTO to entity
        Book book = modelMapper.map(bookRequestDTO, Book.class);
        // Save to database
        Book savedBook = bookRepository.save(book);

        // Convert to DTO and return
        return modelMapper.map(savedBook, BookDTO.class);
    }

    // Updates an existing book
    @Override
    public BookDTO updateBook(int id, BookRequestDTO bookRequestDTO) {
        // Find existing book
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found with id: "+id));

        // Map updated data to existing entity
        modelMapper.map(bookRequestDTO, existingBook);

        // Save updated book
        Book updatedBook = bookRepository.save(existingBook);

        // Convert to DTO and return
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    // Deletes a book by its ID
    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

}
