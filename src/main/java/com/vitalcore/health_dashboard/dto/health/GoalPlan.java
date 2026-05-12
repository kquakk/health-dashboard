package com.vitalcore.health_dashboard.dto.health;

public record GoalPlan(
    String focusHeadline,
    int dailyProteinGrams,
    double dailyWaterLiters,
    String weeklyCheckpoint
) {
}
