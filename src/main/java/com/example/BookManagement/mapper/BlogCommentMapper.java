package com.example.BookManagement.mapper;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.entity.BlogComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogCommentMapper {

    @Mapping(target = "username", source = "user.username")  // comment.getUser().getUsername()
    @Mapping(target = "blogId", source = "blog.id")          // comment.getBlog().getId()
    BlogCommentDTO toDTO(BlogComment blogComment);
}
