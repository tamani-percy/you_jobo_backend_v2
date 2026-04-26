package com.spring.job_tracker.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewRequest {
    private Long id;
    private Long jobApplicationId;

    private LocalDateTime scheduledAt;
    private Integer durationMinutes;
    private String locationOrLink;
    private String interviewerName;
    private String interviewerEmail;
    private String notes;

    private String interviewType;
    private String interviewStage;
    private String interviewResult;
}
