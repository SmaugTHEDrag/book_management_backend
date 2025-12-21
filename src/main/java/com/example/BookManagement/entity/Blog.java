package com.example.BookManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

/*
* Entity of blog
* Blog can have lots of likes, comments;
*/
@Entity
@Table (name = "blogs")
@Getter
@Setter
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Auto-generated primary key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user that post the blog

    @Column(name = "title", nullable = false)
    private String title; // Blog Title

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content; // Blog content

    @Column(name = "image_url", length = 500)
    private String image; // image URL on the blog (optional)

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt; // Timestamp for blog creation

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt; // Timestamp for blog last update

    // ---- RELATIONSHIP ----

    // One blog can have many likes
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogLike> blogLikes;

    // One blog can have many comments
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogComment> blogComments;
}
