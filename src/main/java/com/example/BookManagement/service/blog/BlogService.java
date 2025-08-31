package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
import com.example.BookManagement.entity.Role;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.repository.IBlogRepository;
import com.example.BookManagement.repository.IBookRepository;
import com.example.BookManagement.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Service implementation for managing blogs.
 * This class contains the main business logic for creating, reading,
 * updating, and deleting blogs, as well as mapping comments and replies.
 */
@Service
public class BlogService implements IBlogService{

    @Autowired
    private IBlogRepository blogRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Recursively map a {@link BlogComment} entity to {@link BlogCommentDTO},
     * including all nested replies.
     *
     * @param comment the blog comment entity
     * @return a BlogCommentDTO containing comment details and nested replies
     */
    private BlogCommentDTO mapComment(BlogComment comment) {
        BlogCommentDTO dto = modelMapper.map(comment, BlogCommentDTO.class);
        dto.setUsername(comment.getUser().getUsername());

        // Map replies if available
        if (comment.getChildComment() != null && !comment.getChildComment().isEmpty()) {
            dto.setReplies(comment.getChildComment().stream()
                    .map(this::mapComment)
                    .collect(Collectors.toList()));
        } else {
            dto.setReplies(Collections.emptyList());
        }

        return dto;
    }

    /**
     * Map a {@link Blog} entity to {@link BlogDTO}, including top-level comments
     * and their nested replies, as well as like count.
     *
     * @param blog the blog entity
     * @return a BlogDTO with blog details, like count, and mapped comments
     */
    private BlogDTO mapBlogWithComments(Blog blog) {
        BlogDTO dto = modelMapper.map(blog, BlogDTO.class);

        // Set like count
        dto.setLikeCount(blog.getBlogLikes() != null ? (long) blog.getBlogLikes().size() : 0);

        // Map only top-level comments
        if (blog.getBlogComments() != null) {
            dto.setComments(blog.getBlogComments().stream()
                    .filter(c -> c.getParentComment() == null)   // only top-level
                    .map(this::mapComment)
                    .collect(Collectors.toList()));
        } else {
            dto.setComments(Collections.emptyList());
        }

        return dto;
    }

    /**
     * Get all blogs with their comments and like counts.
     *
     * @return a list of BlogDTO objects containing blog details
     */
    @Override
    public List<BlogDTO> getAllBlogs() {
        return blogRepository.findAll().stream()
                .map(this::mapBlogWithComments)
                .collect(Collectors.toList());
    }

    /**
     * Get a single blog by its ID.
     *
     * @param id the blog ID
     * @return BlogDTO containing blog details
     * @throws RuntimeException if the blog is not found
     */
    @Override
    public BlogDTO getBlogById(int id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        return mapBlogWithComments(blog);
    }


    /**
     * Create a new blog post.
     *
     * @param blogRequestDTO DTO containing blog title, content, and optional image
     * @param username       username of the blog owner
     * @return BlogDTO containing the created blog details
     * @throws RuntimeException      if the user is not found
     * @throws IllegalArgumentException if title or content is missing
     */
    @Override
    public BlogDTO createBlog(BlogRequestDTO blogRequestDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Basic validation
        if (blogRequestDTO.getTitle() == null || blogRequestDTO.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (blogRequestDTO.getContent() == null || blogRequestDTO.getContent().isBlank()) {
            throw new IllegalArgumentException("Content is required");
        }

        // Map DTO to entity
        Blog blog = modelMapper.map(blogRequestDTO, Blog.class);
        blog.setUser(user);

        Blog saved = blogRepository.save(blog);

        return modelMapper.map(saved, BlogDTO.class);
    }

    /**
     * Update an existing blog post.
     * Only the blog owner or an admin can update.
     *
     * @param id             blog ID
     * @param blogRequestDTO DTO containing updated blog fields
     * @param username       username of the blog owner or admin
     * @return BlogDTO containing the updated blog details
     * @throws RuntimeException     if the blog is not found
     * @throws AccessDeniedException if the user does not have permission
     */
    @Override
    public BlogDTO updateBlog(int id, BlogRequestDTO blogRequestDTO, String username) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        // Update only provided fields
        if (blogRequestDTO.getTitle() != null && !blogRequestDTO.getTitle().isBlank()) {
            blog.setTitle(blogRequestDTO.getTitle());
        }
        if (blogRequestDTO.getContent() != null && !blogRequestDTO.getContent().isBlank()) {
            blog.setContent(blogRequestDTO.getContent());
        }
        if (blogRequestDTO.getImage() != null) {
            blog.setImage(blogRequestDTO.getImage());
        }

        Blog updatedBlog = blogRepository.save(blog);
        return modelMapper.map(updatedBlog, BlogDTO.class);
    }

    /**
     * Delete a blog post.
     * Only the blog owner or an admin can delete.
     *
     * @param id       blog ID
     * @param username username of the blog owner or admin
     * @throws RuntimeException     if the blog is not found
     * @throws AccessDeniedException if the user does not have permission
     */
    @Override
    public void deleteBlog(int id, String username) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        blogRepository.delete(blog);
    }
}
