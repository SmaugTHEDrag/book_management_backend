package com.example.BookManagement.service;

import com.example.BookManagement.dto.BookDTO;
import com.example.BookManagement.dto.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.exception.ResourceNotFoundException;
import com.example.BookManagement.repository.IBookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {
    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
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
