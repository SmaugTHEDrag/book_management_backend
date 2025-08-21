package com.example.BookManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

/*
 * Entity of book
 * Book can be favorite by many user
 */
@Entity
@Data
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Auto-generated primary key

    @Column(name = "bookTitle", nullable=false)
    private String title; // Book Title

    @Column(name = "authorName",length = 100, nullable=false)
    private String author; // Book Author

    @Column(name = "imageURL")
    private String image; // URL of the Book Cover Image

    @Column(nullable=false)
    private String category; // Book Category (ex: Fiction, Romance, etc)

    @Column(name = "bookDescription", columnDefinition = "TEXT")
    private String description; // Book Description

    @Column(name = "bookPDFURL")
    private String pdf; // URL to PDF file of the book

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt; // Timestamp of book creation

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt; // Timestamp of book last update

    // ---- RELATIONSHIP ----

    // One book can be favorite by many user;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorite;
}
