package com.juanlopezaranzazu.springboot_api_user_posts.service;

import com.juanlopezaranzazu.springboot_api_user_posts.dto.CategoryRequest;
import com.juanlopezaranzazu.springboot_api_user_posts.dto.CategoryResponse;
import com.juanlopezaranzazu.springboot_api_user_posts.entity.Category;
import com.juanlopezaranzazu.springboot_api_user_posts.exception.BadRequestException;
import com.juanlopezaranzazu.springboot_api_user_posts.exception.ResourceNotFoundException;
import com.juanlopezaranzazu.springboot_api_user_posts.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // obtener las categorias
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> CategoryResponse.fromEntity(category))
                .collect(Collectors.toList());
    }

    // obtener una categoria por su id
    public Optional<CategoryResponse> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> CategoryResponse.fromEntity(category));
    }

    // crear una categoria
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        // verificar que la categoria no existe
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new BadRequestException("La categoría ya existe");
        }
        // crear una categoria
        Category newCategory = new Category();
        newCategory.setName(categoryRequest.getName());
        // guardar la categoria
        categoryRepository.save(newCategory);

        return CategoryResponse.fromEntity(newCategory);
    }

    // editar una categoria por su id
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        // verificar que la categoria existe
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        category.setName(categoryRequest.getName());
        // editar la categoria
        Category updatedCategory = categoryRepository.save(category);

        return CategoryResponse.fromEntity(updatedCategory);
    }

    // eliminar una categoria por su id
    public void deleteCategory(Long id) {
        // verificar que la categoria existe
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada");
        }
        categoryRepository.deleteById(id);
    }
}
