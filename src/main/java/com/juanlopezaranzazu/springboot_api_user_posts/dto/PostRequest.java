package com.juanlopezaranzazu.springboot_api_user_posts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {
    private String title;
    private String content;
    private Long categoryId; // categoria del post
}
