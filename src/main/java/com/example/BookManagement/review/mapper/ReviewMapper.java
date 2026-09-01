package com.example.BookManagement.review.mapper;

import com.example.BookManagement.review.dto.ReviewDTO;
import com.example.BookManagement.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "book.title", target = "title")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(source = "user.username", target = "username")
    ReviewDTO toDTO(Review review);

    List<ReviewDTO> toListDTO(List<Review> reviews);
}
