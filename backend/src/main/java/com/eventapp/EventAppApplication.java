package com.eventapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Main class of the EventApp application.
 *
 * <p>This class starts the Spring Boot application
 * and initializes the server.</p>
 */

@SpringBootApplication
@EntityScan("com.eventapp.model.entities")
public class EventAppApplication {

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(EventAppApplication.class, args);
    }
}

