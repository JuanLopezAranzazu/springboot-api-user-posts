package com.juanlopezaranzazu.springboot_api_user_posts.controller;

import com.juanlopezaranzazu.springboot_api_user_posts.dto.AuthResponse;
import com.juanlopezaranzazu.springboot_api_user_posts.dto.LoginRequest;
import com.juanlopezaranzazu.springboot_api_user_posts.dto.RegisterRequest;
import com.juanlopezaranzazu.springboot_api_user_posts.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    // login de usuarios
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    // registro de usuarios
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }
}
