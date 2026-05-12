package com.vitalcore.health_dashboard.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class HealthAssessmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAssessmentReturnsPersonalizedSummary() throws Exception {
        mockMvc.perform(post("/api/assessments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "weight": 180,
                      "weightUnit": "lbs",
                      "height": 70,
                      "heightUnit": "ft/in",
                      "age": 27,
                      "goal": "Build muscle"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.profile.weightKg").value(81.6))
            .andExpect(jsonPath("$.profile.heightDisplay").value("5'10\""))
            .andExpect(jsonPath("$.plan.dailyProteinGrams").value(160))
            .andExpect(jsonPath("$.bmiCategory").value("Overweight"));
    }

    @Test
    void getAssessmentReturnsStoredResult() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/api/assessments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "weight": 82,
                      "weightUnit": "kg",
                      "height": 182,
                      "heightUnit": "cm",
                      "age": 31,
                      "goal": "Improve consistency"
                    }
                    """))
            .andExpect(status().isCreated())
            .andReturn();

        String response = createResult.getResponse().getContentAsString();
        String assessmentId = response.split("\"id\":\"")[1].split("\"")[0];

        mockMvc.perform(get("/api/assessments/{assessmentId}", assessmentId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.profile.goal").value("Improve consistency"));
    }

    @Test
    void loginEndpointAcceptsFrontendCredentials() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "username": "coach_nate",
                      "password": "secret123"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("coach_nate"))
            .andExpect(jsonPath("$.redirectPath").value("/form"));
    }
}
