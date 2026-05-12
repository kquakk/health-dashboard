package com.vitalcore.health_dashboard.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must be 50 characters or fewer")
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 120, message = "Password must be between 4 and 120 characters")
    String password
) {
}
