package com.example.BookManagement.controller;

import com.example.BookManagement.dto.favorite.FavoriteDTO;
import com.example.BookManagement.dto.favorite.FavoriteRequestDTO;
import com.example.BookManagement.service.favorite.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*
 * REST Controller for managing user's favorite books.
 * Handles adding, removing, and retrieving favorite books for the logged-in user.
 */
@RestController
@RequestMapping("api/favorites")
public class FavoriteController {

    @Autowired
    private IFavoriteService favoriteService; // Service layer for favorite operations

    //Get all favorite books for the current logged-in user.
    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getFavorites(Principal principal) {
        String username = principal.getName();
        System.out.println("Logged in username: " + username); // Optional: logging for debug
        return ResponseEntity.ok(favoriteService.getAllFavorites(principal.getName()));
    }

    // Add a book to the current user's favorites.
    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(@RequestBody FavoriteRequestDTO request, Principal principal) {
        return ResponseEntity.ok(favoriteService.addFavorite(request, principal.getName()));
    }

    // Remove a book from the current user's favorites.
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Integer bookId, Principal principal) {
        favoriteService.removeFavorite(bookId, principal.getName());
        return ResponseEntity.ok("Book removed from favorites");
    }

}
