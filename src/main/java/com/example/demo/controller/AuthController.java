package com.example.demo.controller;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/protected")
    public ResponseEntity<MessageResponse> protectedRoute(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(new MessageResponse("You accessed a protected route as " + userDetails.getUsername()));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        authService.logout(authorizationHeader);
        return ResponseEntity.ok(new MessageResponse("Token invalidated successfully"));
    }
}
