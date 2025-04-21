package com.cimback.simba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimback.simba.model.Summary;
import com.cimback.simba.model.User;
import com.cimback.simba.service.SummaryService;
import com.cimback.simba.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private SummaryService summaryService;

    @PostMapping("/register")
    public User register(@RequestBody User user) 
    {
        return service.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody User user) 
    {
        return service.login(user);
    }
     @PostMapping("/summary")
    public Summary generateSummary(@RequestParam String username, @RequestBody String url) {
        return summaryService.generateSummary(username, url);
    }

    @GetMapping("/history")
    public List<Summary> getHistory(@RequestParam String username) {
        return summaryService.getHistory(username);
    }

}
