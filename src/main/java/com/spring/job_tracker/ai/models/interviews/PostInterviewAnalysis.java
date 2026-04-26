package com.spring.job_tracker.ai.models.interviews;

import com.spring.job_tracker.models.JobApplication;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(
        name = "post_interview_analyses"
)
public class PostInterviewAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String confidenceAssessment;

    @OneToOne
    @JoinColumn(name = "job_application_id", nullable = false, unique = true)
    private JobApplication jobApplication;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "post_interview_missed_opportunities_analysis",
            joinColumns = @JoinColumn(name = "post_interview_analyses_id")
    )
    @Column(name = "missed_opportunities")
    @OrderColumn(name = "position")
    private List<String> missedOpportunities;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "post_interview_next_steps_analysis",
            joinColumns = @JoinColumn(name = "post_interview_analyses_id")
    )
    @Column(name = "next_steps")
    @OrderColumn(name = "position")
    private List<String> nextSteps;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "post_interview_weaknesses_analysis",
            joinColumns = @JoinColumn(name = "post_interview_analyses_id")
    )
    @Column(name = "weaknesses")
    @OrderColumn(name = "position")
    private List<String> weaknesses;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "post_interview_strengths_analysis",
            joinColumns = @JoinColumn(name = "post_interview_analyses_id")
    )
    @Column(name = "strengths")
    @OrderColumn(name = "position")
    private List<String> strengths;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}