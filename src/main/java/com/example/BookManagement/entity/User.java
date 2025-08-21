package com.example.BookManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

/*
* Entity of user (ADMIN or CUSTOMER)
* User can have lots of favorites books, blogs, blog likes and blog comments;
*/
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Auto-generated primary key

    @Column(nullable = false, unique = true, length = 50)
    private String username;   // Unique username

    @Column(nullable = false)
    private String password; // Encrypted password

    @Column(nullable = false, unique = true)
    private String email;  // Unique email address

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // ADMIN or USER

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;  // Timestamp of user creation

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt; // Timestamp of user last update

    // ---- RELATIONSHIP ----

    // One user can have many favorites;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorite;

    // One user can have many blogs;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Blog> blogs;

    // One user can like many blogs;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogLike> blogLikes;

    // One user can comment on many blogs;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogComment> blogComments;
}
