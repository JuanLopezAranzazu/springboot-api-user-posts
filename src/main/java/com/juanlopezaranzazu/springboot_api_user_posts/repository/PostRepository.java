package com.juanlopezaranzazu.springboot_api_user_posts.repository;

import com.juanlopezaranzazu.springboot_api_user_posts.entity.Post;
import com.juanlopezaranzazu.springboot_api_user_posts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // obtener los posts de un usuario
    List<Post> findByUser(User user);
}
