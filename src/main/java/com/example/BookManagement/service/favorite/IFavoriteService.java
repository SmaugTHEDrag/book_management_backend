package com.example.BookManagement.service.favorite;

import com.example.BookManagement.dto.favorite.FavoriteDTO;
import com.example.BookManagement.dto.favorite.FavoriteRequestDTO;

import java.util.List;

public interface IFavoriteService {

    List<FavoriteDTO> getAllFavorites(String username);

    // one user can favorite a book only once
    FavoriteDTO addFavorite(FavoriteRequestDTO favoriteRequestDTO, String username);

    void removeFavorite(Integer bookId, String username);

}
