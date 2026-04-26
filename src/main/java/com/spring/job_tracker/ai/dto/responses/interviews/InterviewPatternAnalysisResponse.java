package com.spring.job_tracker.ai.dto.responses.interviews;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InterviewPatternAnalysisResponse {
    private Long id;
    private String overallAssessment;
    private List<String> recurringStrengths;
    private List<String> recommendedFocusAreas;
    private List<String> skillGaps;
    private List<String> behavioralPatterns;
    private List<String> recurringWeaknesses;
    private LocalDateTime createdAt;
    private Long jobApplicationId;
}
