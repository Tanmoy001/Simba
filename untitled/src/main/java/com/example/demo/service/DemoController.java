package com.example.demo.controller;

import com.example.demo.service.ScalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private final ScalaService scalaService;

    @Autowired
    public DemoController(ScalaService scalaService) {
        this.scalaService = scalaService;
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(required = false, defaultValue = "World") String name) {
        return scalaService.createGreeting(name);
    }

    @GetMapping("/calculate")
    public String calculate(@RequestParam int a, @RequestParam int b) {
        return "Scala calculated: " + scalaService.addNumbers(a, b);
    }
}