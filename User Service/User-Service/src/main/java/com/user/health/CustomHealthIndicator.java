// Class: CustomHealthIndicator.java
package com.user.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        // Example logic
        boolean appHealthy = true;
        if (appHealthy) {
            return Health.up().withDetail("customCheck", "User Service is healthy").build();
        } else {
            return Health.down().withDetail("customCheck", "Something's wrong").build();
        }
    }
}
