package com.cimback.simba.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.cimback.simba.logging.ApiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cimback.simba.model.Summary;
import com.cimback.simba.model.SummaryDTO;
import com.cimback.simba.model.User;
import com.cimback.simba.repo.SummaryRepo;
import com.cimback.simba.repo.UserRepo;
import com.cimback.simba.service.SummaryService;
import com.cimback.simba.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SummaryRepo summaryRepo;

    private final ApiLogger apiLogger;

    public UserController(ApiLogger apiLogger) {
        this.apiLogger = apiLogger;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        ResponseEntity<?> res = service.register(user);
        String responseBody = "Resource created successfully";
        apiLogger.log("/api/register", "registeruser", HttpStatus.CREATED.value(), responseBody);
        System.out.println("User registered: " + res);
        return res;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            System.out.println("Login attempt for user: " + user.getUsername()); // assuming you have username field
            ResponseEntity<?> res = service.login(user);
            System.out.println("Login result: " + res);
            String responseBody = "Resource with ID " ;
            apiLogger.log("/api/login", "user", HttpStatus.OK.value(), responseBody);
            return res;
        } catch (HttpMessageNotReadableException e) {
            System.err.println("Invalid JSON format: " + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid request format");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        System.out.println("User logged out: ");
        String responseBody = "Resource with ID " ;
        apiLogger.log("/api/logout", "user", HttpStatus.OK.value(), responseBody);
        return ResponseEntity.ok().body("Logout successful");
    }

    @PostMapping("/summary")
    public ResponseEntity<String> generateSummary(@RequestParam String username, @RequestBody String url) {

        // System.out.println("summ called");
          String summary = summaryService.generateSummary(url, username);
        String responseBody = "Resource with ID " ;
        apiLogger.log("/api/summary", "user", HttpStatus.OK.value(), responseBody);
        // System.out.println("Summary generated: " + summary);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/history")
    public ResponseEntity<List<SummaryDTO>> getHistory(@RequestParam(required = false) Long userId) {
        List<Summary> summaries = summaryRepo.findAllByUserIdOrderByCreatedAtDesc(userId);
        List<SummaryDTO> summaryDTOs = summaries.stream()
                .map(SummaryDTO::new) //Aplly constructor to each summary object
                .collect(Collectors.toList()); // gathers the results back into a list.
        String responseBody = "Resource with ID " ;
        apiLogger.log("/api/history", "user", HttpStatus.OK.value(), responseBody);

        return ResponseEntity.ok(summaryDTOs);
    }





    
    // @GetMapping("/me")
    // @PreAuthorize("isAuthenticated()")
    // public ResponseEntity<?> getUserDetails(Authentication authentication) {
    // // CustomUserDetails should implement UserDetails and have getId()
    // User userDetails = (User) authentication.getPrincipal();
    // Long userId = userDetails.getId(); // or getUsername(), depending on how you
    // store it

    // User user = userRepo.findById(userId)
    // .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // return ResponseEntity.ok(Map.of(
    // "success", true,
    // "user", user
    // ));
    // }

}
