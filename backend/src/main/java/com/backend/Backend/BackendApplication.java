package com.backend.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext; 

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BackendApplication.class, args);
        Dev obj = context.getBean(Dev.class);
        obj.build();
    }
}
