package com.example.BookManagement.mapper;

import com.example.BookManagement.dto.book.BookDTO;
import com.example.BookManagement.dto.book.BookRequestDTO;
import com.example.BookManagement.entity.Book;
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
