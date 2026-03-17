package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDefaultUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("1234"));
                user.setRole("ROLE_USER");
                userRepository.save(user);
            }
        };
    }
}
