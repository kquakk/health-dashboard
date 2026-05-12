package com.vitalcore.health_dashboard.service;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.vitalcore.health_dashboard.dto.health.GoalPlan;
import com.vitalcore.health_dashboard.dto.health.HealthAssessmentRequest;
import com.vitalcore.health_dashboard.dto.health.HealthAssessmentResponse;
import com.vitalcore.health_dashboard.dto.health.HealthProfileSummary;
import com.vitalcore.health_dashboard.exception.ResourceNotFoundException;

@Service
public class HealthAssessmentService {

    private static final double OUNCES_TO_LITERS = 0.0295735;
    private final Map<UUID, HealthAssessmentResponse> assessments = new ConcurrentHashMap<>();

    public HealthAssessmentResponse createAssessment(HealthAssessmentRequest request) {
        double weightValue = request.weight().doubleValue();
        double heightValue = request.height().doubleValue();

        double weightLbs = roundToSingleDecimal(request.weightUnit().toPounds(weightValue));
        double weightKg = roundToSingleDecimal(request.weightUnit().toKilograms(weightValue));
        double heightCm = roundToSingleDecimal(request.heightUnit().toCentimeters(heightValue));
        double heightMeters = heightCm / 100.0;
        double bmi = roundToSingleDecimal(weightKg / (heightMeters * heightMeters));
        String bmiCategory = bmiCategory(bmi);

        String goal = request.goal().trim();
        GoalPlan plan = buildGoalPlan(goal, weightLbs);

        HealthAssessmentResponse response = new HealthAssessmentResponse(
            UUID.randomUUID(),
            Instant.now(),
            new HealthProfileSummary(
                request.age(),
                goal,
                weightLbs,
                weightKg,
                heightCm,
                formatHeight(heightCm)
            ),
            plan,
            bmi,
            bmiCategory,
            List.of(
                "BMI currently sits in the " + bmiCategory.toLowerCase(Locale.ROOT) + " range. Use it as a trend signal, not a diagnosis.",
                "A protein target around " + plan.dailyProteinGrams() + " grams per day supports your goal of " + goal + ".",
                "Hydration around " + plan.dailyWaterLiters() + " liters per day is a reasonable starting point for consistency."
            ),
            buildNextSteps(goal)
        );

        assessments.put(response.id(), response);
        return response;
    }

    public HealthAssessmentResponse getAssessment(UUID assessmentId) {
        HealthAssessmentResponse response = assessments.get(assessmentId);

        if (response == null) {
            throw new ResourceNotFoundException("No assessment exists for id " + assessmentId);
        }

        return response;
    }

    private GoalPlan buildGoalPlan(String goal, double weightLbs) {
        String normalizedGoal = goal.toLowerCase(Locale.ROOT);
        double proteinMultiplier;
        String focusHeadline;
        String weeklyCheckpoint;

        if (containsAny(normalizedGoal, "lose", "cut", "fat", "lean")) {
            proteinMultiplier = 0.80;
            focusHeadline = "Create a steady calorie deficit while protecting muscle with lifting, walking, and high protein.";
            weeklyCheckpoint = "Check scale trend, workout performance, and hunger cues once per week before changing intake.";
        } else if (containsAny(normalizedGoal, "gain", "build", "bulk", "muscle", "strength")) {
            proteinMultiplier = 0.90;
            focusHeadline = "Support muscle gain with progressive overload, reliable meals, and enough recovery to keep performance climbing.";
            weeklyCheckpoint = "Review bodyweight trend, sleep quality, and training progression every 7 days.";
        } else {
            proteinMultiplier = 0.70;
            focusHeadline = "Build a repeatable wellness routine focused on consistency, recovery, and sustainable nutrition habits.";
            weeklyCheckpoint = "Review energy, sleep, hydration, and routine consistency at the end of each week.";
        }

        int dailyProteinGrams = roundToNearestFive(weightLbs * proteinMultiplier);
        double dailyWaterLiters = roundToSingleDecimal(weightLbs * 0.5 * OUNCES_TO_LITERS);

        return new GoalPlan(focusHeadline, dailyProteinGrams, dailyWaterLiters, weeklyCheckpoint);
    }

    private List<String> buildNextSteps(String goal) {
        String normalizedGoal = goal.toLowerCase(Locale.ROOT);

        if (containsAny(normalizedGoal, "lose", "cut", "fat", "lean")) {
            return List.of(
                "Aim for 8,000 to 10,000 daily steps before making aggressive diet changes.",
                "Keep resistance training in the week so weight loss does not come at the cost of strength.",
                "Hold your plan for 10 to 14 days before deciding whether to adjust calories."
            );
        }

        if (containsAny(normalizedGoal, "gain", "build", "bulk", "muscle", "strength")) {
            return List.of(
                "Prioritize progressive overload on a few core lifts instead of changing the whole plan every week.",
                "Distribute protein across 3 to 5 meals so recovery is easier to sustain.",
                "Watch the monthly trend, not single weigh-ins, when deciding whether intake is high enough."
            );
        }

        return List.of(
            "Track sleep, hydration, and movement alongside body metrics so progress has context.",
            "Choose 1 or 2 anchor habits you can repeat daily before adding more complexity.",
            "Re-run the intake after a few weeks to compare how your routine is shifting."
        );
    }

    private boolean containsAny(String value, String... matches) {
        for (String match : matches) {
            if (value.contains(match)) {
                return true;
            }
        }

        return false;
    }

    private String bmiCategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        }
        if (bmi < 25.0) {
            return "Healthy";
        }
        if (bmi < 30.0) {
            return "Overweight";
        }
        return "Obesity";
    }

    private String formatHeight(double heightCm) {
        double totalInches = heightCm / 2.54;
        int feet = (int) Math.floor(totalInches / 12.0);
        int inches = (int) Math.round(totalInches - (feet * 12.0));

        if (inches == 12) {
            feet += 1;
            inches = 0;
        }

        return feet + "'" + inches + "\"";
    }

    private int roundToNearestFive(double value) {
        return (int) (Math.round(value / 5.0) * 5);
    }

    private double roundToSingleDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
