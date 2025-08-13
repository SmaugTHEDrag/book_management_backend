package com.example.BookManagement.service;

import com.example.BookManagement.dto.BookDTO;
import com.example.BookManagement.dto.BookPageResponse;
import com.example.BookManagement.dto.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.exception.ResourceNotFoundException;
import com.example.BookManagement.form.BookFilterForm;
import com.example.BookManagement.repository.IBookRepository;
import com.example.BookManagement.specification.BookSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService implements IBookService {
    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookPageResponse getAllBooks(BookFilterForm form, Pageable pageable) {
        Specification<Book> where = BookSpecification.buildWhere(form);
        Page<Book> books = bookRepository.findAll(where, pageable);
        Page<BookDTO> bookDTOs = books.map(book -> modelMapper.map(book, BookDTO.class));

        // Build response
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

    @Override
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public BookDTO createBook(BookRequestDTO bookRequestDTO) {
        // Map new data from DTO to entity
        Book book = modelMapper.map(bookRequestDTO, Book.class);
        // Store
        Book savedBook = bookRepository.save(book);
        // Return DTO
        return modelMapper.map(savedBook, BookDTO.class);
    }

    @Override
    public BookDTO updateBook(int id, BookRequestDTO bookRequestDTO) {
        // Check if exist book
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found with id: "+id));
        // Map new data from DTO to entity
        modelMapper.map(bookRequestDTO, existingBook);
        // Store it
        Book updatedBook = bookRepository.save(existingBook);
        // Return DTO
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

}
