package com.example.BookManagement.service;

import com.example.BookManagement.dto.FavoriteDTO;
import com.example.BookManagement.dto.FavoriteRequestDTO;
import com.example.BookManagement.entity.Book;
import com.example.BookManagement.entity.Favorite;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.repository.IBookRepository;
import com.example.BookManagement.repository.IFavoriteRepository;
import com.example.BookManagement.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavoriteService implements IFavoriteService{

    @Autowired
    private IFavoriteRepository favoriteRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FavoriteDTO addFavorite(FavoriteRequestDTO request, String username) {
        User user = userRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (favoriteRepository.existsByUserIdAndBookId(user.getId(), book.getId())) {
            throw new RuntimeException("Book already in favorites");
        }
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBook(book);
        return modelMapper.map(favoriteRepository.save(favorite), FavoriteDTO.class);
    }

    @Override
    public void removeFavorite(Integer bookId, String username) {
        User user = userRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
        Favorite favorite = favoriteRepository.findByUserIdAndBookId(user.getId(), bookId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        favoriteRepository.delete(favorite);
    }

    @Override
    public List<FavoriteDTO> getAllFavorites(String username) {
        User user = userRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Favorite> favorites = favoriteRepository.findByUserId(user.getId());

        return favorites.stream()
                .map(fav -> {
                    Book book = fav.getBook();
                    FavoriteDTO dto = new FavoriteDTO();
                    dto.setId(fav.getId());
                    dto.setBookId(book.getId());
                    dto.setTitle(book.getTitle());
                    dto.setAuthor(book.getAuthor());
                    dto.setCategory(book.getCategory());
                    dto.setImage(book.getImage());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
