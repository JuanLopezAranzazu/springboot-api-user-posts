package com.juanlopezaranzazu.springboot_api_user_posts.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
