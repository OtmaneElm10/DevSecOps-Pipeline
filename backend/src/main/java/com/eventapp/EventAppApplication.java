package com.eventapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EventAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventAppApplication.class, args);
    }

    @GetMapping("/api/test")
    public String test() {
        return "test";
    }
}
