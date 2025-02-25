package com.juanlopezaranzazu.springboot_api_user_posts.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    // fecha de la orden
    @Column(nullable = false, name = "post_date")
    private LocalDateTime postDate = LocalDateTime.now();

    // relacion muchos a 1 con usuarios
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // relacion muchos a 1 con categorias
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
