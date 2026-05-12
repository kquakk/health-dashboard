package com.vitalcore.health_dashboard.api;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitalcore.health_dashboard.dto.health.HealthAssessmentRequest;
import com.vitalcore.health_dashboard.dto.health.HealthAssessmentResponse;
import com.vitalcore.health_dashboard.service.HealthAssessmentService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/assessments")
public class HealthAssessmentController {

    private final HealthAssessmentService healthAssessmentService;

    public HealthAssessmentController(HealthAssessmentService healthAssessmentService) {
        this.healthAssessmentService = healthAssessmentService;
    }

    @PostMapping
    public ResponseEntity<HealthAssessmentResponse> createAssessment(
        @Valid @RequestBody HealthAssessmentRequest request
    ) {
        HealthAssessmentResponse response = healthAssessmentService.createAssessment(request);
        return ResponseEntity
            .created(URI.create("/api/assessments/" + response.id()))
            .body(response);
    }

    @GetMapping("/{assessmentId}")
    public HealthAssessmentResponse getAssessment(@PathVariable UUID assessmentId) {
        return healthAssessmentService.getAssessment(assessmentId);
    }
}
