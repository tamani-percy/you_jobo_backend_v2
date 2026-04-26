package com.spring.job_tracker.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewResponse {
    private Long id;
    private Long jobApplicationId;

    private LocalDateTime scheduledAt;
    private Integer durationMinutes;
    private String locationOrLink;
    private String interviewerName;
    private String interviewerEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String notes;

    private String interviewType;
    private String interviewStage;
    private String interviewResult;
}
