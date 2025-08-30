package com.example.BookManagement.config;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
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

        // ================================
        // Custom mapping: Blog -> BlogDTO
        // Map the 'username' field in BlogDTO from Blog.user.username
        // This allows DTO to carry username directly instead of entire User object
        // ================================
        modelMapper.typeMap(Blog.class, BlogDTO.class).addMappings(mapper ->
                mapper.map(src -> src.getUser().getUsername(), BlogDTO::setUsername)
        );

        // ================================
        // Custom mapping: BlogComment -> BlogCommentDTO
        // Map the 'username' field in BlogCommentDTO from BlogComment.user.username
        // This ensures DTO contains the author's username without embedding full User entity
        // ================================
        modelMapper.typeMap(BlogComment.class, BlogCommentDTO.class).addMappings(mapper ->
                mapper.map(src -> src.getUser().getUsername(), BlogCommentDTO::setUsername)
        );

        return modelMapper;
    }
}
