package com.juanlopezaranzazu.springboot_api_user_posts.controller;

import com.juanlopezaranzazu.springboot_api_user_posts.dto.PostRequest;
import com.juanlopezaranzazu.springboot_api_user_posts.dto.PostResponse;
import com.juanlopezaranzazu.springboot_api_user_posts.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    @Autowired
    private PostService postService;

    // obtener todos los posts
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // obtener todos los posts por usuario
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PostResponse>> getPostsByUser() {
        return ResponseEntity.ok(postService.getPostsByUser());
    }

    // obtener un post por su id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        Optional<PostResponse> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // crear un post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.createPost(postRequest));
    }

    // actualizar un post por su id
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.updatePost(id, postRequest));
    }

    // eliminar un post por su id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
