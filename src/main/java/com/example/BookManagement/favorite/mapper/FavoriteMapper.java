package com.example.BookManagement.favorite.mapper;

import com.example.BookManagement.favorite.dto.FavoriteDTO;
import com.example.BookManagement.favorite.entity.Favorite;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {
    FavoriteDTO toDTO(Favorite save);

    List<FavoriteDTO> toListDTO(List<Favorite> byUserId);
}
