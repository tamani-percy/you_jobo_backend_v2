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
        name = "interview_patterns_analyses"
)
public class InterviewPatternAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String overallAssessment;

    @OneToOne
    @JoinColumn(name = "job_application_id", nullable = false, unique = true)
    private JobApplication jobApplication;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "interview_patterns_skill_gaps_analysis",
            joinColumns = @JoinColumn(name = "interview_patterns_analyses_id")
    )
    @Column(name = "skill_gaps")
    @OrderColumn(name = "position")
    private List<String> skillGaps;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "interview_patterns_recurring_strengths_gaps_analysis",
            joinColumns = @JoinColumn(name = "interview_patterns_analyses_id")
    )
    @Column(name = "recurring_strengths")
    @OrderColumn(name = "position")
    private List<String> recurringStrengths;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "interview_patterns_recurring_weaknesses_gaps_analysis",
            joinColumns = @JoinColumn(name = "interview_patterns_analyses_id")
    )
    @Column(name = "recurring_weaknesses")
    @OrderColumn(name = "position")
    private List<String> recurringWeaknesses;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "interview_patterns_behavioral_patterns_gaps_analysis",
            joinColumns = @JoinColumn(name = "interview_patterns_analyses_id")
    )
    @Column(name = "behavioral_patterns")
    @OrderColumn(name = "position")
    private List<String> behavioralPatterns;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "interview_patterns_recommended_focus_areas_gaps_analysis",
            joinColumns = @JoinColumn(name = "interview_patterns_analyses_id")
    )
    @Column(name = "recommended_focus_areas")
    @OrderColumn(name = "position")
    private List<String> recommendedFocusAreas;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}