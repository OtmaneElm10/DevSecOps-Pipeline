package com.eventapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main class of the EventApp application.
 *
 * <p>This class starts the Spring Boot application
 * and initializes the server.</p>
 *
 * <p>It also provides a simple test endpoint
 * to check if the application is running.</p>
 */
@SpringBootApplication
@RestController
public class EventAppApplication {

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(EventAppApplication.class, args);
    }

    /**
     * Simple test endpoint.
     *
     * @return "test" if the server is running
     */
    @GetMapping("/api/test")
    public String test() {
        return "test";
    }
}

