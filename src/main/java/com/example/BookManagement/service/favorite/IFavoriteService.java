package com.example.BookManagement.service.favorite;

import com.example.BookManagement.dto.favorite.FavoriteDTO;
import com.example.BookManagement.dto.favorite.FavoriteRequestDTO;

import java.util.List;

public interface IFavoriteService {

    // Get all favorite books of a user
    List<FavoriteDTO> getAllFavorites(String username);

    // Add a book to the user's favorites
    FavoriteDTO addFavorite(FavoriteRequestDTO favoriteRequestDTO, String username);

    // Remove a book from the user's favorites
    void removeFavorite(Integer bookId, String username);

}
