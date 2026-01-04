package com.example.BookManagement.mapper;

import com.example.BookManagement.dto.favorite.FavoriteDTO;
import com.example.BookManagement.entity.Favorite;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {
    FavoriteDTO toDTO(Favorite save);

    List<FavoriteDTO> toListDTO(List<Favorite> byUserId);
}
