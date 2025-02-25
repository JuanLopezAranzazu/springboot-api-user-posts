package com.juanlopezaranzazu.springboot_api_user_posts.dto;

import com.juanlopezaranzazu.springboot_api_user_posts.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postDate; // fecha del post
    private String userName; // nombre del usuario
    private String categoryName; // nombre de la categoria

    public static PostResponse fromEntity(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getPostDate(),
                post.getUser().getName(),
                post.getCategory().getName()
        );
    }
}
