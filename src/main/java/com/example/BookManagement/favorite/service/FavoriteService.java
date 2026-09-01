package com.example.BookManagement.favorite.service;

import com.example.BookManagement.favorite.dto.FavoriteDTO;
import com.example.BookManagement.favorite.dto.FavoriteRequestDTO;
import com.example.BookManagement.book.entity.Book;
import com.example.BookManagement.favorite.entity.Favorite;
import com.example.BookManagement.user.entity.User;
import com.example.BookManagement.common.exception.ResourceNotFoundException;
import com.example.BookManagement.favorite.mapper.FavoriteMapper;
import com.example.BookManagement.book.repository.IBookRepository;
import com.example.BookManagement.favorite.repository.IFavoriteRepository;
import com.example.BookManagement.user.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FavoriteService implements IFavoriteService{

    private final IFavoriteRepository favoriteRepository;

    private final IUserRepository userRepository;

    private final IBookRepository bookRepository;

    private final FavoriteMapper favoriteMapper;

    @Override
    public List<FavoriteDTO> getAllFavorites(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Favorite> favorites = favoriteRepository.findByUserId(user.getId());

        return favoriteMapper.toListDTO(favorites);
    }

    // Adds a book to the user's favorites.
    @Override
    public FavoriteDTO addFavorite(FavoriteRequestDTO request, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        // one user can favorite a book only once
        if (favoriteRepository.existsByUserIdAndBookId(user.getId(), book.getId())) {
            throw new IllegalArgumentException("Book already in favorites");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBook(book);

        return favoriteMapper.toDTO(favoriteRepository.save(favorite));
    }

    @Override
    public void removeFavorite(Integer bookId, String username) {

        User user = userRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Favorite favorite = favoriteRepository.findByUserIdAndBookId(user.getId(), bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }
}
