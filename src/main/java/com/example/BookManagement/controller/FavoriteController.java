package com.example.BookManagement.controller;

import com.example.BookManagement.dto.favorite.FavoriteDTO;
import com.example.BookManagement.dto.favorite.FavoriteRequestDTO;
import com.example.BookManagement.service.favorite.IFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@Tag(name = "Favorite API", description = "APIs for managing user's favorite books")
@RequiredArgsConstructor
public class FavoriteController {

    private final IFavoriteService favoriteService;

    @Operation(summary = "Get all favorite books of the current user")
    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getFavorites(Principal principal) {
        return ResponseEntity.ok(favoriteService.getAllFavorites(principal.getName()));
    }

    @Operation(summary = "Add favorite to current user's favorites")
    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(@RequestBody @Valid FavoriteRequestDTO request, Principal principal) {
        return ResponseEntity.ok(favoriteService.addFavorite(request, principal.getName()));
    }

    @Operation(summary = "Remove favorite from current user's favorites")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Integer bookId, Principal principal) {
        favoriteService.removeFavorite(bookId, principal.getName());
        return ResponseEntity.ok("Book removed from favorites");
    }

}
