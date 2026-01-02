package com.example.BookManagement.mapper;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BlogCommentMapper.class)
public interface BlogMapper {

    // Comment
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "blogId", source = "blog.id")
    BlogCommentDTO toCommentDto(BlogComment comment);

    // Entity → DTO
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "username", source = "user.username")    // blog.getUser().getUsername() → blogDTO.username
    BlogDTO toDTO(Blog blog);

    Blog toEntity(BlogRequestDTO blogRequestDTO);
}
