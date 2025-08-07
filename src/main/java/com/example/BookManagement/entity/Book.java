package com.example.BookManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bookTitle", nullable=false)
    private String title;

    @Column(name = "authorName",length = 100, nullable=false)
    private String author;

    @Column(name = "imageURL")
    private String image;

    @Column(nullable=false)
    private String category;

    @Column(name = "bookDescription", columnDefinition = "TEXT")
    private String description;

    @Column(name = "bookPDFURL")
    private String pdf;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorite;
}
