package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.mapper.BlogCommentMapper;
import com.example.BookManagement.repository.IBlogCommentRepository;
import com.example.BookManagement.repository.IBlogRepository;
import com.example.BookManagement.repository.IUserRepository;
import com.example.BookManagement.service.aimoderation.IAIModerationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BlogCommentService implements IBlogCommentService {

    private final IBlogCommentRepository commentRepository;

    private final IBlogRepository blogRepository;

    private final IUserRepository userRepository;

    private final BlogCommentMapper blogCommentMapper;

    private final IAIModerationService moderationService;

    // Convert comment entity to DTO (including replies)
    private BlogCommentDTO mapToCommentDTO(BlogComment blogComment) {
        BlogCommentDTO dto = blogCommentMapper.toDTO(blogComment);
        if (blogComment.getUser() != null) {
            dto.setUsername(blogComment.getUser().getUsername());
        }
        dto.setBlogId(blogComment.getBlog() != null ? blogComment.getBlog().getId() : null);
        if (blogComment.getChildComment() != null) {
            dto.setReplies(blogComment.getChildComment().stream()
                    .map(this::mapToCommentDTO)
                    .toList());
        } else {
            dto.setReplies(Collections.emptyList());
        }
        return dto;
    }

    // Add new comment or reply
    @Override
    public BlogCommentDTO addComment(BlogCommentRequestDTO request, String username) {
        Blog blog = blogRepository.findById(request.getBlogId())
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BlogComment comment = new BlogComment();
        comment.setBlog(blog);
        comment.setUser(user);
        if (request.getContent() != null && !request.getContent().isBlank()) {
            moderationService.checkComment(request.getContent(), "Comment contains inappropriate content");
            comment.setContent(request.getContent());
        }
        comment.setImage(request.getImage());

        if (request.getParentCommentId() != null) {
            BlogComment parent = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParentComment(parent);
            // parent.childComment
        }

        BlogComment saved = commentRepository.save(comment);
        return mapToCommentDTO(saved);
    }

    // Update comment content or image
    @Override
    public BlogCommentDTO updateComment(Integer commentId, BlogCommentRequestDTO request, String username) {
        BlogComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (request.getContent() != null && !request.getContent().isBlank()) {
            moderationService.checkComment(request.getContent(), "Comment contains inappropriate content");
            comment.setContent(request.getContent());
        }
        if (request.getImage() != null) comment.setImage(request.getImage());

        BlogComment updated = commentRepository.save(comment);
        return mapToCommentDTO(updated);
    }


    // Delete a comment
    @Override
    public void deleteComment(Integer commentId, String username) {
        BlogComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }


    // Get comments of a blog (only top-level, replies included)
    @Override
    public List<BlogCommentDTO> getCommentsByBlog(Integer blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        return commentRepository.findAllByBlogAndParentCommentIsNull(blog)
                .stream()
                .map(this::mapToCommentDTO)
                .toList();
    }
}
