package com.example.demo.service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthService(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService,
            JwtUtil jwtUtil,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token, userDetails.getUsername(), "Login successful");
    }

    public void register(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Username and password are required");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    public void logout(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header with Bearer token is required");
        }

        String token = authorizationHeader.substring(7);
        tokenBlacklistService.blacklistToken(token, jwtUtil.extractExpiration(token));
    }
}
