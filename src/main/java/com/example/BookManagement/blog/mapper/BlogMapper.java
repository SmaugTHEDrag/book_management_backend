package com.example.BookManagement.blog.mapper;

import com.example.BookManagement.blog.dto.BlogCommentDTO;
import com.example.BookManagement.blog.dto.BlogDTO;
import com.example.BookManagement.blog.dto.BlogRequestDTO;
import com.example.BookManagement.blog.entity.Blog;
import com.example.BookManagement.blog.entity.BlogComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BlogCommentMapper.class)
public interface BlogMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "blogId", source = "blog.id")
    BlogCommentDTO toCommentDto(BlogComment comment);

    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "username", source = "user.username")
    BlogDTO toDTO(Blog blog);

    Blog toEntity(BlogRequestDTO blogRequestDTO);
}
