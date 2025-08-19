package com.example.BookManagement.config;

import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Blog -> BlogDTO
        modelMapper.typeMap(Blog.class, BlogDTO.class).addMappings(mapper ->
                mapper.map(src -> src.getUser().getUsername(), BlogDTO::setUsername)
        );

        // BlogComment -> BlogCommentDTO
        modelMapper.typeMap(BlogComment.class, BlogCommentDTO.class).addMappings(mapper ->
                mapper.map(src -> src.getUser().getUsername(), BlogCommentDTO::setUsername)
        );

        return modelMapper;
    }
}
