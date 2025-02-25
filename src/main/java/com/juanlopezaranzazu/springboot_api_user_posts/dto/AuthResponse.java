package com.juanlopezaranzazu.springboot_api_user_posts.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
}
