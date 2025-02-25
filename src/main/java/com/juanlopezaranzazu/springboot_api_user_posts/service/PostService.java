package com.juanlopezaranzazu.springboot_api_user_posts.service;

import com.juanlopezaranzazu.springboot_api_user_posts.dto.PostRequest;
import com.juanlopezaranzazu.springboot_api_user_posts.dto.PostResponse;
import com.juanlopezaranzazu.springboot_api_user_posts.entity.Category;
import com.juanlopezaranzazu.springboot_api_user_posts.entity.Post;
import com.juanlopezaranzazu.springboot_api_user_posts.entity.User;
import com.juanlopezaranzazu.springboot_api_user_posts.exception.ForbiddenException;
import com.juanlopezaranzazu.springboot_api_user_posts.exception.ResourceNotFoundException;
import com.juanlopezaranzazu.springboot_api_user_posts.repository.CategoryRepository;
import com.juanlopezaranzazu.springboot_api_user_posts.repository.PostRepository;
import com.juanlopezaranzazu.springboot_api_user_posts.repository.UserRepository;
import com.juanlopezaranzazu.springboot_api_user_posts.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    // obtener los posts
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> PostResponse.fromEntity(post))
                .collect(Collectors.toList());
    }

    // obtener los pots por usuario
    public List<PostResponse> getPostsByUser() {
        // obtener el usuario autenticado
        String username = AuthUtil.getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // obtener los posts del usuario
        List<Post> posts = postRepository.findByUser(user);

        return posts.stream()
                .map(post -> PostResponse.fromEntity(post))
                .collect(Collectors.toList());
    }

    // obtener un post por su id
    public Optional<PostResponse> getPostById(Long id) {
        return postRepository.findById(id)
                .map(post -> PostResponse.fromEntity(post));
    }

    // crear un post
    public PostResponse createPost(PostRequest postRequest) {
        // obtener el usuario autenticado
        String username = AuthUtil.getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // verificar que la categoria existe
        Category category = categoryRepository.findById(postRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        // crear el post
        Post newPost = new Post();
        newPost.setTitle(postRequest.getTitle());
        newPost.setContent(postRequest.getContent());
        newPost.setPostDate(LocalDateTime.now()); // obtener la fecha
        newPost.setUser(user);
        newPost.setCategory(category);
        // guardar el post
        postRepository.save(newPost);

        return PostResponse.fromEntity(newPost);
    }

    // editar un post por su id
    public PostResponse updatePost(Long id, PostRequest postRequest) {
        // obtener el usuario autenticado
        String username = AuthUtil.getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // verificar que existe el post
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));

        // verificar que el usuario es el propietario
        if (!post.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("No tienes permiso para editar este post");
        }

        // verificar que la categoria existe
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCategory(category);
        // editar el post
        Post updatedPost = postRepository.save(post);

        return PostResponse.fromEntity(updatedPost);
    }

    // eliminar un post por su id
    public void deletePost(Long id) {
        // obtener el usuario autenticado
        String username = AuthUtil.getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // verificar que existe el post
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));

        // verificar que el usuario es el propietario
        if (!post.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("No tienes permiso para editar este post");
        }

        // eliminar post
        postRepository.delete(post);
    }
}
