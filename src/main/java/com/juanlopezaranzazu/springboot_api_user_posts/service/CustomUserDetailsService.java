package com.juanlopezaranzazu.springboot_api_user_posts.service;

import com.juanlopezaranzazu.springboot_api_user_posts.entity.UserPrincipal;
import com.juanlopezaranzazu.springboot_api_user_posts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserPrincipal::new)
                .orElseThrow(() -> {
                    System.out.println("Usuario no encontrado");
                    return new UsernameNotFoundException("Usuario no encontrado");
                });
    }
}
