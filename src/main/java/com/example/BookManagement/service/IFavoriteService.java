package com.example.BookManagement.service;

import com.example.BookManagement.dto.FavoriteDTO;
import com.example.BookManagement.dto.FavoriteRequestDTO;

import java.util.List;

public interface IFavoriteService {
    FavoriteDTO addFavorite(FavoriteRequestDTO favoriteRequestDTO, String username);
    void removeFavorite(Integer bookId, String username);
    List<FavoriteDTO> getAllFavorites(String username);
}
