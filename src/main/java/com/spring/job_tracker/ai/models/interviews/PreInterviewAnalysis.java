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
        name = "pre_interview_analyses"
)
public class PreInterviewAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "job_application_id", nullable = false, unique = true)
    private JobApplication jobApplication;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "pre_interview_likely_questions_analysis",
            joinColumns = @JoinColumn(name = "pre_interview_analyses_id")
    )
    @Column(name = "likely_questions")
    @OrderColumn(name = "position")
    private List<String> likelyQuestions;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "pre_interview_technical_topics_analysis",
            joinColumns = @JoinColumn(name = "pre_interview_analyses_id")
    )
    @Column(name = "technical_topics")
    @OrderColumn(name = "position")
    private List<String> technicalTopics;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "pre_interview_preparation_tips_analysis",
            joinColumns = @JoinColumn(name = "pre_interview_analyses_id")
    )
    @Column(name = "preparation_tips")
    @OrderColumn(name = "position")
    private List<String> preparationTips;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "pre_interview_focus_areas_analysis",
            joinColumns = @JoinColumn(name = "pre_interview_analyses_id")
    )
    @Column(name = "focus_areas")
    @OrderColumn(name = "position")
    private List<String> focusAreas;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "pre_interview_confidence_tips_analysis",
            joinColumns = @JoinColumn(name = "pre_interview_analyses_id")
    )
    @Column(name = "confidence_tips")
    @OrderColumn(name = "position")
    private List<String> confidenceTips;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}