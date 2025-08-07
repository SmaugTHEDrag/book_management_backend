package com.example.BookManagement.controller;

import com.example.BookManagement.dto.FavoriteDTO;
import com.example.BookManagement.dto.FavoriteRequestDTO;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.service.IFavoriteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/favorites")
public class FavoriteController {

    @Autowired
    private IFavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(@RequestBody FavoriteRequestDTO request, Principal principal) {
        return ResponseEntity.ok(favoriteService.addFavorite(request, principal.getName()));
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Integer bookId, Principal principal) {
        favoriteService.removeFavorite(bookId, principal.getName());
        return ResponseEntity.ok("Book removed from favorites");
    }

    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getFavorites(Principal principal) {
        String username = principal.getName();
        System.out.println("Logged in username: " + username);
        return ResponseEntity.ok(favoriteService.getAllFavorites(principal.getName()));
    }
}
