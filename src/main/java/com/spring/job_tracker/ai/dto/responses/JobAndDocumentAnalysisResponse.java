package com.spring.job_tracker.ai.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobAndDocumentAnalysisResponse {
    private Long id;
    private Integer matchScore;
    private List<String> strengths;
    private List<String> gaps;
    private List<String> suggestedKeywords;
    private String priorityRecommendation;
    private String summary;
    private LocalDateTime createdAt;
    private Long jobApplicationId;
}