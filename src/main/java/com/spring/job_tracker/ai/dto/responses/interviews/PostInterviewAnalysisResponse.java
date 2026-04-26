package com.spring.job_tracker.ai.dto.responses.interviews;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostInterviewAnalysisResponse {
    private Long id;
    private String summary;
    private String confidenceAssessment;
    private List<String> nextSteps;
    private List<String> missedOpportunities;
    private List<String> weaknesses;
    private List<String> strengths;
    private LocalDateTime createdAt;
    private Long jobApplicationId;
}
