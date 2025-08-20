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

@Service
public class BlogService implements IBlogService{

    @Autowired
    private IBlogRepository blogRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BlogDTO> getAllBlogs() {
        return blogRepository.findAll().stream()
                .map(this::mapBlogWithComments)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDTO getBlogById(int id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        return mapBlogWithComments(blog);
    }

    // ----------------------------
// map Blog + comments + nested replies
    private BlogDTO mapBlogWithComments(Blog blog) {
        BlogDTO dto = modelMapper.map(blog, BlogDTO.class);

        // likeCount
        dto.setLikeCount(blog.getBlogLikes() != null ? (long) blog.getBlogLikes().size() : 0);

        // only top-level comments
        if (blog.getBlogComments() != null) {
            dto.setComments(blog.getBlogComments().stream()
                    .filter(c -> c.getParentComment() == null)   // lọc top-level
                    .map(this::mapComment)
                    .collect(Collectors.toList()));
        } else {
            dto.setComments(Collections.emptyList());
        }

        return dto;
    }

    // recursive map for BlogComment -> BlogCommentDTO
    private BlogCommentDTO mapComment(BlogComment comment) {
        BlogCommentDTO dto = modelMapper.map(comment, BlogCommentDTO.class);
        dto.setUsername(comment.getUser().getUsername());

        // nested replies
        if (comment.getChildComment() != null && !comment.getChildComment().isEmpty()) {
            dto.setReplies(comment.getChildComment().stream()
                    .map(this::mapComment)
                    .collect(Collectors.toList()));
        } else {
            dto.setReplies(Collections.emptyList());
        }

        return dto;
    }


    @Override
    public BlogDTO createBlog(BlogRequestDTO blogRequestDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate cơ bản
        if (blogRequestDTO.getTitle() == null || blogRequestDTO.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (blogRequestDTO.getContent() == null || blogRequestDTO.getContent().isBlank()) {
            throw new IllegalArgumentException("Content is required");
        }

        // Map từ DTO sang Entity
        Blog blog = modelMapper.map(blogRequestDTO, Blog.class);
        blog.setUser(user);

        Blog saved = blogRepository.save(blog);

        return modelMapper.map(saved, BlogDTO.class);
    }

    @Override
    public BlogDTO updateBlog(int id, BlogRequestDTO blogRequestDTO, String username) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        // ✅ Kiểm tra quyền
        if (!blog.getUser().getUsername().equals(username) && !isAdmin(username)) {
            throw new AccessDeniedException("You do not have permission to update this blog");
        }

        // Chỉ update nếu trường được truyền
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

    @Override
    public void deleteBlog(int id, String username) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        // ✅ Kiểm tra quyền
        if (!blog.getUser().getUsername().equals(username) && !isAdmin(username)) {
            throw new AccessDeniedException("You do not have permission to delete this blog");
        }

        blogRepository.delete(blog);
    }

    private boolean isAdmin(String username) {
        return userRepository.findByUsername(username)
                .map(user -> user.getRole() == Role.ADMIN)  // 👈 so sánh enum
                .orElse(false);
    }

}
