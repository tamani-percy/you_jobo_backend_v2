package com.spring.job_tracker.models;

import com.spring.job_tracker.models.statuses.InterviewResult;
import com.spring.job_tracker.models.statuses.InterviewStage;
import com.spring.job_tracker.models.statuses.InterviewType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "interview_stage_id", nullable = false)
    private InterviewStage interviewStage;

    @ManyToOne
    @JoinColumn(name = "interview_type_id", nullable = false)
    private InterviewType interviewType;

    @ManyToOne
    @JoinColumn(name = "interview_result_id", nullable = false)
    private InterviewResult interviewResult;

    private LocalDateTime scheduledAt;

    private Integer durationMinutes;

    private String locationOrLink;

    private String interviewerName;

    private String interviewerEmail;


    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "job_application_id")
    private JobApplication jobApplication;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
