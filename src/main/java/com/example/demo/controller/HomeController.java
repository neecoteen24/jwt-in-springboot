package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
                "message", "JWT Authentication API is running",
                "availableEndpoints", new String[]{
                        "POST /auth/register",
                        "POST /auth/login",
                        "GET /auth/protected",
                        "POST /auth/logout"
                }
        );
    }
}
