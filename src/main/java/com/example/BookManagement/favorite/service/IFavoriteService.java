package com.example.BookManagement.favorite.service;

import com.example.BookManagement.favorite.dto.FavoriteDTO;
import com.example.BookManagement.favorite.dto.FavoriteRequestDTO;

import java.util.List;

public interface IFavoriteService {

    List<FavoriteDTO> getAllFavorites();

    // one user can favorite a book only once
    FavoriteDTO addFavorite(FavoriteRequestDTO favoriteRequestDTO);

    void removeFavorite(Integer bookId);

}

