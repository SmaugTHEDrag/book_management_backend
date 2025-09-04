package com.example.BookManagement.service.favorite;

import com.example.BookManagement.dto.favorite.FavoriteDTO;
import com.example.BookManagement.dto.favorite.FavoriteRequestDTO;

import java.util.List;

/*
 * Service interface for managing user favorite books.
 */
public interface IFavoriteService {

    /**
     * Get all favorite books of a user
     *
     * @param username the username of the user
     * @return list of favorite books as DTOs
     */
    List<FavoriteDTO> getAllFavorites(String username);

    /**
     * Add a book to the user's favorites
     *
     * @param favoriteRequestDTO details of the book to be added
     * @param username the username of the user
     * @return the created FavoriteDTO
     */
    FavoriteDTO addFavorite(FavoriteRequestDTO favoriteRequestDTO, String username);

    /**
     * Remove a book from the user's favorites
     *
     * @param bookId ID of the book to remove
     * @param username the username of the user
     */
    void removeFavorite(Integer bookId, String username);

}
