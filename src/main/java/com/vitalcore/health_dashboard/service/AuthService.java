package com.vitalcore.health_dashboard.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vitalcore.health_dashboard.dto.auth.LoginRequest;
import com.vitalcore.health_dashboard.dto.auth.LoginResponse;

@Service
public class AuthService {

    public LoginResponse login(LoginRequest request) {
        String username = request.username().trim();

        return new LoginResponse(
            "demo-" + UUID.randomUUID(),
            "Bearer",
            username,
            toDisplayName(username),
            Instant.now().plus(8, ChronoUnit.HOURS),
            "/form",
            "Demo login accepted. The current frontend can now continue into the health intake flow."
        );
    }

    private String toDisplayName(String username) {
        if (username.isBlank()) {
            return "VitalCore User";
        }

        String[] parts = username.replaceAll("[^A-Za-z0-9]+", " ").trim().split("\\s+");
        StringBuilder builder = new StringBuilder();

        for (String part : parts) {
            if (part.isBlank()) {
                continue;
            }

            if (!builder.isEmpty()) {
                builder.append(' ');
            }

            builder
                .append(Character.toUpperCase(part.charAt(0)))
                .append(part.substring(1).toLowerCase());
        }

        return builder.length() == 0 ? "VitalCore User" : builder.toString();
    }
}
