package com.example.BookManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/*
*
* Entity of like on blog
* Each user can like a blog once only (enforced by unique constraints)
*
* */

@Entity
@Data
@Table(name = "blog_like", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"blog_id", "user_id"})
})
public class BlogLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Auto-generated primary key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog; // The blog that is liked

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who like the blog

    @Column(name = "liked_at")
    @CreationTimestamp
    private LocalDateTime likedAt; // Timestamp when user like the blog

}


