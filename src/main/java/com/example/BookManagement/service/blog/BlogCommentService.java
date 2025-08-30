package com.example.BookManagement.service.blog;

import com.example.BookManagement.dto.blog.BlogCommentDTO;
import com.example.BookManagement.dto.blog.BlogCommentRequestDTO;
import com.example.BookManagement.entity.Blog;
import com.example.BookManagement.entity.BlogComment;
import com.example.BookManagement.entity.User;
import com.example.BookManagement.repository.IBlogCommentRepository;
import com.example.BookManagement.repository.IBlogRepository;
import com.example.BookManagement.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/*
 * Service implementation for managing blog comments.
 * Handles adding, updating, deleting, and retrieving comments, including nested replies.
 */
@Service
public class BlogCommentService implements IBlogCommentService {

    @Autowired
    private IBlogCommentRepository commentRepository;

    @Autowired
    private IBlogRepository blogRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /*
     * Map a BlogComment entity to BlogCommentDTO recursively.
     * Includes nested replies.
     *
     * @param blogComment the comment entity
     * @return BlogCommentDTO with comment details and nested replies
     */
    private BlogCommentDTO mapToCommentDTO(BlogComment blogComment) {
        BlogCommentDTO dto = modelMapper.map(blogComment, BlogCommentDTO.class);
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

    /*
     * Add a comment to a blog post.
     *
     * @param blogId   ID of the blog to comment on
     * @param request  DTO containing comment content, optional image, and optional parent comment ID
     * @param username Username of the user adding the comment
     * @return BlogCommentDTO of the created comment
     * @throws RuntimeException if blog, user, or parent comment (if specified) is not found
     */
    @Override
    public BlogCommentDTO addComment(Integer blogId, BlogCommentRequestDTO request, String username) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BlogComment comment = new BlogComment();
        comment.setBlog(blog);
        comment.setUser(user);
        comment.setContent(request.getContent());
        comment.setImage(request.getImage());

        if (request.getParentCommentId() != null) {
            BlogComment parent = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParentComment(parent);
            // parent.childComment tự động cập nhật khi saved vì cascade
        }

        BlogComment saved = commentRepository.save(comment);
        return mapToCommentDTO(saved);
    }

    /*
     * Update an existing comment.
     * Only the comment owner is allowed to update.
     *
     * @param commentId ID of the comment to update
     * @param request   DTO containing updated content and/or image
     * @param username  Username of the comment owner
     * @return BlogCommentDTO of the updated comment
     * @throws RuntimeException if comment is not found or user is not authorized
     */
    @Override
    public BlogCommentDTO updateComment(Integer commentId, BlogCommentRequestDTO request, String username) {
        BlogComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // chỉ author hoặc admin mới được edit — bạn có thể thay check role nếu cần
        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to edit this comment");
        }

        if (request.getContent() != null) comment.setContent(request.getContent());
        if (request.getImage() != null) comment.setImage(request.getImage());

        BlogComment updated = commentRepository.save(comment);
        return mapToCommentDTO(updated);
    }

    /*
     * Delete a comment.
     * Only the comment owner is allowed to delete.
     *
     * @param commentId ID of the comment to delete
     * @param username  Username of the comment owner
     * @throws RuntimeException if comment is not found or user is not authorized
     */
    @Override
    public void deleteComment(Integer commentId, String username) {
        BlogComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

    /*
     * Get all top-level comments for a specific blog post.
     * Nested replies are included in the response.
     *
     * @param blogId ID of the blog
     * @return List of BlogCommentDTOs for the blog
     * @throws RuntimeException if blog is not found
     */
    @Override
    public List<BlogCommentDTO> getCommentsByBlog(Integer blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        // lấy chỉ top-level comments (parent = null) và Map trả về
        return commentRepository.findAllByBlogAndParentCommentIsNull(blog)
                .stream()
                .map(this::mapToCommentDTO)
                .toList();
    }
}
