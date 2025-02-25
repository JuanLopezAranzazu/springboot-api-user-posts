package com.juanlopezaranzazu.springboot_api_user_posts.repository;

import com.juanlopezaranzazu.springboot_api_user_posts.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // verificar que la categoria esta en la base de datos
    boolean existsByName(String name);
}
