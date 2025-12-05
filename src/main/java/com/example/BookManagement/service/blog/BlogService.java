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
import com.example.BookManagement.service.file.FileUploadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Service implementation for managing blogs
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

    @Autowired
    private FileUploadService fileUploadService;

    // Convert BlogComment entity to DTO (includes nested replies)
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

    // Convert Blog entity to DTO (includes top-level comments and like count)
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

    // Get all blogs with their comments and like counts.
    @Override
    public List<BlogDTO> getAllBlogs() {
        return blogRepository.findAll().stream()
                .map(this::mapBlogWithComments)
                .collect(Collectors.toList());
    }

    // Get a single blog by its ID.
    @Override
    public BlogDTO getBlogById(int id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        return mapBlogWithComments(blog);
    }


    // Create a new blog post.
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

    // Update a blog
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

    // Delete a blog post by its ID
    @Override
    public void deleteBlog(int id, String username) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        blogRepository.delete(blog);
    }

    @Override
    public BlogDTO createBlogWithUpload(String title, String content, MultipartFile image, String imageURL, String username) {
        try {
            String uploadedImageUrl = null;
            if(image != null && image.isEmpty()){
                uploadedImageUrl = fileUploadService.uploadFile(image, "blogs/images");
            } else if (imageURL != null && !imageURL.isBlank()){
                uploadedImageUrl = imageURL;
            }
            // Build DTO
            BlogRequestDTO dto = new BlogRequestDTO(title, content, uploadedImageUrl);

            // Call blog method
            return createBlog(dto, username);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
