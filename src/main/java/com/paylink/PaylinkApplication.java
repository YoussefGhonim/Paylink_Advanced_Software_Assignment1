
package com.paylink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the PayLink application.
 */
@SpringBootApplication
public class PaylinkApplication {

    /**
     * Starts the PayLink application.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(PaylinkApplication.class, args);
    }
}
