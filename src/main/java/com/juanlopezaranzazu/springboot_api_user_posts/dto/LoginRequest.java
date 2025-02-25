package com.juanlopezaranzazu.springboot_api_user_posts.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
