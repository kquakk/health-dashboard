package com.vitalcore.health_dashboard.dto.health;

import java.math.BigDecimal;

import com.vitalcore.health_dashboard.model.HeightUnit;
import com.vitalcore.health_dashboard.model.WeightUnit;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HealthAssessmentRequest(
    @NotNull(message = "Weight is required")
    @DecimalMin(value = "1.0", message = "Weight must be greater than zero")
    BigDecimal weight,

    @NotNull(message = "Weight unit is required")
    WeightUnit weightUnit,

    @NotNull(message = "Height is required")
    @DecimalMin(value = "1.0", message = "Height must be greater than zero")
    BigDecimal height,

    @NotNull(message = "Height unit is required")
    HeightUnit heightUnit,

    @NotNull(message = "Age is required")
    @Min(value = 13, message = "Age must be at least 13")
    @Max(value = 120, message = "Age must be 120 or younger")
    Integer age,

    @NotBlank(message = "Goal is required")
    @Size(max = 120, message = "Goal must be 120 characters or fewer")
    String goal
) {
}
