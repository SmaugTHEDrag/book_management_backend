package com.example.BookManagement.config;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogLikeDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
import com.example.BookManagement.entity.BlogLike;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Spring configuration class for defining beans used across the application.
 * Here we configure ModelMapper to map entities to DTOs with custom mappings.
 */
@Configuration
public class Config {

    /*
     * Configure and provide a ModelMapper bean.
     * ModelMapper is used to convert between entity objects and DTOs.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Map Blog -> BlogDTO (username from Blog.user.username)
        modelMapper.typeMap(Blog.class, BlogDTO.class).addMappings(mapper ->
                mapper.map(src -> src.getUser().getUsername(), BlogDTO::setUsername)
        );

        // Map BlogComment -> BlogCommentDTO (username from BlogComment.user.username)
        modelMapper.typeMap(BlogComment.class, BlogCommentDTO.class).addMappings(mapper ->
                mapper.map(src -> src.getUser().getUsername(), BlogCommentDTO::setUsername)
        );

        // Map BlogLike -> BlogLikeDTO
        modelMapper.typeMap(BlogLike.class, BlogLikeDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getBlog() != null ? src.getBlog().getId() : null, BlogLikeDTO::setBlogId);
            mapper.map(src -> src.getUser() != null ? src.getUser().getUsername() : null, BlogLikeDTO::setUsername);
        });

        return modelMapper;
    }
}
