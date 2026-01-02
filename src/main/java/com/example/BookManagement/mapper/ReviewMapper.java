package com.example.BookManagement.mapper;

import com.example.BookManagement.dto.review.ReviewDTO;
import com.example.BookManagement.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "book.title", target = "title")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(source = "user.username", target = "username")
    ReviewDTO toDTO(Review review);

    List<ReviewDTO> toListDTO(List<Review> reviews);
}
