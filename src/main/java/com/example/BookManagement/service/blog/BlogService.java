package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogDTO;
import com.example.BookManagement.dto.blog.BlogRequestDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.exception.ResourceNotFoundException;
import com.example.BookManagement.mapper.BlogMapper;
import com.example.BookManagement.repository.IBlogRepository;
import com.example.BookManagement.repository.IUserRepository;
import com.example.BookManagement.service.aimoderation.IAIModerationService;
import com.example.BookManagement.service.file.FileUploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BlogService implements IBlogService{

    private final IBlogRepository blogRepository;

    private final IUserRepository userRepository;

    private final BlogMapper blogMapper;

    private final FileUploadService fileUploadService;

    private final IAIModerationService moderationService;

    // map comment with recursive replies
    private BlogCommentDTO mapComment(BlogComment comment) {
        BlogCommentDTO dto = blogMapper.toCommentDto(comment);
        dto.setUsername(comment.getUser().getUsername());

        // map replies if available
        if (comment.getChildComment() != null && !comment.getChildComment().isEmpty()) {
            dto.setReplies(comment.getChildComment().stream()
                    .map(this::mapComment)
                    .collect(Collectors.toList()));
        } else {
            dto.setReplies(Collections.emptyList());
        }

        return dto;
    }

    // build blog dto with comments and like count
    private BlogDTO mapBlogWithComments(Blog blog) {
        BlogDTO dto = blogMapper.toDTO(blog);

        // simple like count, no extra query
        dto.setLikeCount(blog.getBlogLikes() != null ? (long) blog.getBlogLikes().size() : 0);

        // only load top-level comments
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

    @Override
    public List<BlogDTO> getAllBlogs() {
        return blogRepository.findAll().stream()
                .map(this::mapBlogWithComments)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDTO getBlogById(int id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
        return mapBlogWithComments(blog);
    }

    @Override
    public BlogDTO createBlog(BlogRequestDTO blogRequestDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (blogRequestDTO.getTitle() == null || blogRequestDTO.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }

        if (blogRequestDTO.getContent() == null || blogRequestDTO.getContent().isBlank()) {
            throw new IllegalArgumentException("Content is required");
        }

        // check content before saving
        moderationService.checkComment(blogRequestDTO.getTitle(), "Blog contains inappropriate title");
        moderationService.checkComment(blogRequestDTO.getContent(), "Blog contains inappropriate content");

        Blog blog = blogMapper.toEntity(blogRequestDTO);
        blog.setUser(user);

        Blog saved = blogRepository.save(blog);
        return blogMapper.toDTO(saved);
    }

    @Override
    public BlogDTO updateBlog(int id, BlogRequestDTO blogRequestDTO, String username) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));

        if (blogRequestDTO.getTitle() != null && !blogRequestDTO.getTitle().isBlank()) {
            moderationService.checkComment(blogRequestDTO.getTitle(), "Blog contains inappropriate title");
            blog.setTitle(blogRequestDTO.getTitle());
        }

        if (blogRequestDTO.getContent() != null && !blogRequestDTO.getContent().isBlank()) {
            moderationService.checkComment(blogRequestDTO.getContent(), "Blog contains inappropriate content");
            blog.setContent(blogRequestDTO.getContent());
        }

        if (blogRequestDTO.getImage() != null) {
            blog.setImage(blogRequestDTO.getImage());
        }

        Blog updatedBlog = blogRepository.save(blog);
        return blogMapper.toDTO(updatedBlog);
    }

    @Override
    public void deleteBlog(int id, String username) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        blogRepository.delete(blog);
    }

    // Create a new blog post with image (Cloudinary)
    @Override
    public BlogDTO createBlogWithUpload(String title, String content, MultipartFile image, String imageURL, String username) {
        try {
            String uploadedImageUrl = null;

            // upload image if file exists
            if(image != null && !image.isEmpty()){
                uploadedImageUrl = fileUploadService.uploadFile(image, "blogs/images");
            }

            // fallback to external image url
            else if (imageURL != null && !imageURL.isBlank()){
                uploadedImageUrl = imageURL;
            }

            BlogRequestDTO dto = new BlogRequestDTO(title, content, uploadedImageUrl);
            return createBlog(dto, username);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
