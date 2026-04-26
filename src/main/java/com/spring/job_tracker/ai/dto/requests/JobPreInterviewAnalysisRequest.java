package com.spring.job_tracker.ai.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPreInterviewAnalysisRequest {
    private Long jobApplicationId;
    private Long documentId;
}
