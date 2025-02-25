package com.juanlopezaranzazu.springboot_api_user_posts.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    private String username;
    private String password;
}
