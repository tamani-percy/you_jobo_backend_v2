package com.spring.job_tracker.ai.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class NotesAnalysisResponse {
    private Long id;
    private String summary;
    private List<String> strengths;
    private List<String> concerns;
    private List<String> nextSteps;
    private List<String> patterns;
    private LocalDateTime createdAt;
    private Long jobApplicationId;
}
