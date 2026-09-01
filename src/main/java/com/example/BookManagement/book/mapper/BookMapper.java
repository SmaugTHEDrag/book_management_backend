package com.example.BookManagement.book.mapper;

import com.example.BookManagement.book.dto.BookDTO;
import com.example.BookManagement.book.dto.BookRequestDTO;
import com.example.BookManagement.book.entity.Book;
import com.example.BookManagement.review.mapper.ReviewMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ReviewMapper.class)
public interface BookMapper {

    @Mapping(source = "reviews", target = "reviews")
    BookDTO toDTO(Book book);

    Book toEntity(BookRequestDTO bookRequestDTO);

    void updateEntityFromDTO(BookRequestDTO bookRequestDTO,@MappingTarget Book existingBook);
}
