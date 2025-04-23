package com.cimback.simba.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import com.cimback.simba.model.User;
import com.cimback.simba.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTservice jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public ResponseEntity<?> register(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            System.out.println("Username already taken: " + user.getUsername());
            return ResponseEntity.badRequest().body("Username already taken");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);

        String token = jwtService.generateToken(user.getUsername());

        Map<String, Object> response = new HashMap<>();

        response.put("user_data", Map.of(
                "token", token,
                "username", user.getUsername(),
                "email", user.getEmail(),
                "auth", true));

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> login(User user) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                // Generate JWT token
                String token = jwtService.generateToken(user.getUsername());

                // Fetch complete user details from database
                User authenticatedUser = userRepo.findByUsername(user.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                // Create response with all user details
                Map<String, Object> response = new HashMap<>();
                response.put("user_data", Map.of(
                        "token", token,
                        "Id", authenticatedUser.getId(),
                        "username", authenticatedUser.getUsername(),
                        "email", authenticatedUser.getEmail(),
                        "auth", true));

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }
}
