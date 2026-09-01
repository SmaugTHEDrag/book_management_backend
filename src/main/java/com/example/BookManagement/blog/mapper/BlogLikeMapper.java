package com.example.BookManagement.blog.mapper;

import com.example.BookManagement.blog.dto.BlogLikeDTO;
import com.example.BookManagement.blog.entity.BlogLike;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogLikeMapper {
    BlogLikeDTO toDTO(BlogLike blogLike);
}
