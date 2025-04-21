package com.cimback.simba;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserController {
    @GetMapping("/hello")
    public String greet(HttpServletRequest request) {
        return "Hello, World!" + request.getSession().getId() + " " + request.getSession().getCreationTime() + " " + request.getSession().getLastAccessedTime();
    }
}
