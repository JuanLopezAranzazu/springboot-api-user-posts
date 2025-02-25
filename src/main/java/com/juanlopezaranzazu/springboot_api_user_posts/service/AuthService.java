package com.juanlopezaranzazu.springboot_api_user_posts.service;

import com.juanlopezaranzazu.springboot_api_user_posts.dto.AuthResponse;
import com.juanlopezaranzazu.springboot_api_user_posts.dto.LoginRequest;
import com.juanlopezaranzazu.springboot_api_user_posts.dto.RegisterRequest;
import com.juanlopezaranzazu.springboot_api_user_posts.entity.Role;
import com.juanlopezaranzazu.springboot_api_user_posts.exception.BadRequestException;
import com.juanlopezaranzazu.springboot_api_user_posts.exception.ResourceNotFoundException;
import com.juanlopezaranzazu.springboot_api_user_posts.repository.RoleRepository;
import com.juanlopezaranzazu.springboot_api_user_posts.repository.UserRepository;
import com.juanlopezaranzazu.springboot_api_user_posts.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Service
public class AuthService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    // login de usuarios con jwt
    public AuthResponse login(LoginRequest loginRequest){
        // verificar las credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        if (authentication.isAuthenticated()) {
            // generar token
            String token = jwtService.generateToken(loginRequest.getUsername());;
            return new AuthResponse(token);
        } else {
            throw new UsernameNotFoundException("Usuario incorrecto");
        }
    }

    // registro de usuario con jwt
    public AuthResponse register(RegisterRequest registerRequest){
        // verificar el username
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new BadRequestException("El usuario ya existe");
        }

        // verificar el rol
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Rol USER no encontrado"));

        // crear el usuario
        User newUser = new User();
        newUser.setName(registerRequest.getName());
        newUser.setUsername(registerRequest.getUsername());
        // encriptar contraseña
        newUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        // asignar rol
        newUser.setRoles(Set.of(userRole));
        // guardar usuario
        userRepository.save(newUser);

        // generar token
        String token = jwtService.generateToken(registerRequest.getUsername());;
        return new AuthResponse(token);
    }
}
