
package com.paylink.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides application health-check endpoints.
 */
@RestController
public class HealthController {

    /**
     * Returns the current application status.
     *
     * @return application running message
     */
    @GetMapping("/health")
    public final String health() {
        return "PayLink is running!";
    }
}
