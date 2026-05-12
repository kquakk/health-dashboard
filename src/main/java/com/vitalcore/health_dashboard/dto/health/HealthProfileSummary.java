package com.vitalcore.health_dashboard.dto.health;

public record HealthProfileSummary(
    int age,
    String goal,
    double weightLbs,
    double weightKg,
    double heightCm,
    String heightDisplay
) {
}
