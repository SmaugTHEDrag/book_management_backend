package com.example.BookManagement.favorite.service;

import com.example.BookManagement.favorite.dto.FavoriteDTO;
import com.example.BookManagement.favorite.dto.FavoriteRequestDTO;

import java.util.List;

public interface IFavoriteService {

    List<FavoriteDTO> getAllFavorites(String username);

    // one user can favorite a book only once
    FavoriteDTO addFavorite(FavoriteRequestDTO favoriteRequestDTO, String username);

    void removeFavorite(Integer bookId, String username);

}
