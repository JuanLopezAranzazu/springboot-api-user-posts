package com.juanlopezaranzazu.springboot_api_user_posts.config;

import com.juanlopezaranzazu.springboot_api_user_posts.entity.Role;
import com.juanlopezaranzazu.springboot_api_user_posts.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Component
@Order(1)
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
    }

    // crear los roles por defecto
    private void createRoleIfNotExists(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            Role newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);
            System.out.println("Rol creado: " + roleName);
        }
    }
}

