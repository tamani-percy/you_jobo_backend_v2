package com.spring.job_tracker.ai.dto.responses.interviews;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PreInterviewAnalysisResponse {
    private Long id;
    private List<String> likelyQuestions;
    private List<String> technicalTopics;
    private List<String> preparationTips;
    private List<String> focusAreas;
    private List<String> confidenceTips;
    private LocalDateTime createdAt;
    private Long jobApplicationId;
}
