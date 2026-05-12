package com.vitalcore.health_dashboard.dto.auth;

import java.time.Instant;

public record LoginResponse(
    String token,
    String tokenType,
    String username,
    String displayName,
    Instant expiresAt,
    String redirectPath,
    String message
) {
}
