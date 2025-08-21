package com.example.BookManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

/*
 * Entity of comment on blog
 * Supports nested comments (replies) via parent-child relationship
 */
@Entity
@Data
@Table(name = "blog_comment")
public class BlogComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Auto-generated primary key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog; // The blog that this comment belongs to

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who write this blog

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private BlogComment parentComment; // Parent comment (if owner reply)

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogComment> childComment; // Replies to this comment

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content; // Comment Content

    @Column(name = "image_url", length = 500)
    private String image; // URL of image attached to the comment (optional)

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt; // Timestamp for comment creation

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt; // Timestamp for comment last update
}
