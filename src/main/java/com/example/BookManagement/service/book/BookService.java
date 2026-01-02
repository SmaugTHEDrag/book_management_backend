package com.example.BookManagement.service.book;

import com.example.BookManagement.dto.book.BookDTO;
import com.example.BookManagement.dto.book.BookPageResponse;
import com.example.BookManagement.dto.book.BookRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.entity.Review;
import com.example.BookManagement.exception.ResourceNotFoundException;
import com.example.BookManagement.form.BookFilterForm;
import com.example.BookManagement.mapper.BookMapper;
import com.example.BookManagement.mapper.ReviewMapper;
import com.example.BookManagement.repository.IBookRepository;
import com.example.BookManagement.repository.IReviewRepository;
import com.example.BookManagement.service.file.FileUploadService;
import com.example.BookManagement.specification.BookSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/*
 * Service implementation for managing books
 * Handles business logic and interacts with the repository
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookService implements IBookService {

    private final IBookRepository bookRepository;

    private final BookMapper bookMapper;

    private final FileUploadService fileUploadService;

    private final IReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    private BookDTO mapToBookDTOWithRating(Book book) {
        BookDTO dto = bookMapper.toDTO(book);

        Double avgRating = reviewRepository.findAvgRatingByBookId(book.getId());
        dto.setAvgRating(avgRating != null ? avgRating : 0.0);

        Integer reviewCount = reviewRepository.getReviewCountByBookId(book.getId());
        dto.setReviewCount(reviewCount !=null ? reviewCount:0);

        List<Review> reviews = reviewRepository.findByBookIdWithUser(book.getId()); // query join fetch user
        dto.setReviews(reviewMapper.toListDTO(reviews));

        return dto;
    }

    // Retrieves a paginated list of books based on filter criteria.
    @Override
    public BookPageResponse getAllBooks(BookFilterForm form, Pageable pageable) {
        // Build search/filter conditions
        Specification<Book> where = BookSpecification.buildWhere(form);

        // Get paginated books from database
        Page<Book> books = bookRepository.findAll(where, pageable);

        // Convert Book entities to BookDTOs
        Page<BookDTO> bookDTOs = books.map(this::mapToBookDTOWithRating);

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
    public BookDTO getBookById(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookDTO dto = bookMapper.toDTO(book);

        Double avgRating = reviewRepository.findAvgRatingByBookId(id);
        dto.setAvgRating(avgRating != null ? avgRating : 0.0);
        Integer reviewCount = reviewRepository.getReviewCountByBookId(id);
        dto.setReviewCount(reviewCount !=null ? reviewCount:0);
        return dto;
    }


    // Creates a new book entry
    @Override
    public BookDTO createBook(BookRequestDTO bookRequestDTO) {
        // Map request DTO to entity
        Book book = bookMapper.toEntity(bookRequestDTO);
        // Save to database
        Book savedBook = bookRepository.save(book);

        // Convert to DTO and return
        return bookMapper.toDTO(savedBook);
    }

    // Updates an existing book
    @Override
    public BookDTO updateBook(int id, BookRequestDTO bookRequestDTO) {
        // Find existing book
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Update fields manually
        bookMapper.updateEntityFromDTO(bookRequestDTO, existingBook);

        // Save updated book
        Book updatedBook = bookRepository.save(existingBook);

        // Convert to DTO and return
        return bookMapper.toDTO(updatedBook);
    }

    // Deletes a book by its ID
    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDTO createBookWithUpload(String title, String author, String category, String description, MultipartFile image, MultipartFile pdf) {
        try {
            String pdfUrl = fileUploadService.uploadFile(pdf, "books/pdfs");
            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                imageUrl = fileUploadService.uploadFile(image, "books/images");
            }
            BookRequestDTO dto = new BookRequestDTO(
                    title,
                    author,
                    category,
                    imageUrl,
                    description,
                    pdfUrl
            );
            return createBook(dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
