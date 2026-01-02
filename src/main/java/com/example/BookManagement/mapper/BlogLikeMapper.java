package com.example.BookManagement.mapper;

import com.example.BookManagement.dto.blog.BlogLikeDTO;
import com.example.BookManagement.entity.BlogLike;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogLikeMapper {
    BlogLikeDTO toDTO(BlogLike blogLike);
}
