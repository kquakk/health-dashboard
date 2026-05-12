package com.vitalcore.health_dashboard.dto.health;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record HealthAssessmentResponse(
    UUID id,
    Instant submittedAt,
    HealthProfileSummary profile,
    GoalPlan plan,
    double bmi,
    String bmiCategory,
    List<String> insights,
    List<String> nextSteps
) {
}
