package com.example.BookManagement.service.favorite;

import com.example.BookManagement.dto.favorite.FavoriteDTO;
import com.example.BookManagement.dto.favorite.FavoriteRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.entity.Favorite;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.repository.IBookRepository;
import com.example.BookManagement.repository.IFavoriteRepository;
import com.example.BookManagement.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Service implementation for managing user's favorite books.
 */
@Service
@Transactional
public class FavoriteService implements IFavoriteService{

    @Autowired
    private IFavoriteRepository favoriteRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all favorite books of a user.
     *
     * @param username username of the user whose favorites to retrieve
     * @return list of FavoriteDTO containing book details
     * @throws RuntimeException if the user is not found
     */
    @Override
    public List<FavoriteDTO> getAllFavorites(String username) {
        // Find the user by username
        User user = userRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get all favorites for this user
        List<Favorite> favorites = favoriteRepository.findByUserId(user.getId());

        // Convert favorites to DTOs
        return favorites.stream()
                .map(fav -> {
                    Book book = fav.getBook();
                    FavoriteDTO dto = new FavoriteDTO();
                    dto.setId(fav.getId());
                    dto.setBookId(book.getId());
                    dto.setTitle(book.getTitle());
                    dto.setAuthor(book.getAuthor());
                    dto.setCategory(book.getCategory());
                    dto.setImage(book.getImage());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Adds a book to the user's favorites.
     *
     * @param request  DTO containing the book ID to add
     * @param username username of the user adding the favorite
     * @return FavoriteDTO containing the saved favorite details
     * @throws RuntimeException if the user or book is not found,
     *                          or if the book is already in favorites
     */
    @Override
    public FavoriteDTO addFavorite(FavoriteRequestDTO request, String username) {
        // Find the user by username
        User user = userRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the book by ID
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Check if the book is already in the user's favorites
        if (favoriteRepository.existsByUserIdAndBookId(user.getId(), book.getId())) {
            throw new RuntimeException("Book already in favorites");
        }

        // Create and save the favorite record
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBook(book);

        // Return the saved favorite as DTO
        return modelMapper.map(favoriteRepository.save(favorite), FavoriteDTO.class);
    }

    /**
     * Removes a book from the user's favorites.
     *
     * @param bookId ID of the book to remove
     * @param username username of the user removing the favorite
     * @throws RuntimeException if the user or favorite is not found
     */
    @Override
    public void removeFavorite(Integer bookId, String username) {
        // Find the user by username
        User user = userRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the favorite by user and book ID
        Favorite favorite = favoriteRepository.findByUserIdAndBookId(user.getId(), bookId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));

        // Delete the favorite record
        favoriteRepository.delete(favorite);
    }
}
