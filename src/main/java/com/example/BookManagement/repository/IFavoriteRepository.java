package com.example.BookManagement.repository;

import com.example.BookManagement.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IFavoriteRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findByUserId(Integer userId);
    Optional<Favorite> findByUserIdAndBookId(Integer userId, Integer bookId);
    boolean existsByUserIdAndBookId(Integer userId, Integer bookId);
    void deleteByUserIdAndBookId(Integer userId, Integer bookId);
}
