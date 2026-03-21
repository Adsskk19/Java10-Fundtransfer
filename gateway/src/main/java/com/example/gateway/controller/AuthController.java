package com.example.gateway.controller;

import com.example.gateway.model.User;
import com.example.gateway.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // For demo, hardcoded user
    private final User demoUser = new User("admin", "admin123", "ROLE_ADMIN");

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (demoUser.getUsername().equals(username) && demoUser.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(username, demoUser.getRole());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
