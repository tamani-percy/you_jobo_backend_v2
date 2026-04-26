package com.spring.job_tracker.ai.models;

import com.spring.job_tracker.models.Document;
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
        name = "job_and_document_analyses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"job_application_id", "document_id"})
        }
)
public class JobAndDocumentAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_application_id")
    private JobApplication jobApplication;

    @OneToOne
    @JoinColumn(name = "document_id", nullable = false, unique = true)
    private Document document;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "job_and_document_strengths_analysis",
            joinColumns = @JoinColumn(name = "job_and_document_analyses_id")
    )
    @Column(name = "strength")
    @OrderColumn(name = "position")
    private List<String> strengths;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "job_and_document_gaps_analysis",
            joinColumns = @JoinColumn(name = "job_and_document_analyses_id")
    )
    @Column(name = "gap")
    @OrderColumn(name = "position")
    private List<String> gaps;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "job_and_document_suggested_keyword_analysis",
            joinColumns = @JoinColumn(name = "job_and_document_analyses_id")
    )
    @Column(name = "suggested_keyword")
    @OrderColumn(name = "position")
    private List<String> suggestedKeywords;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private Integer matchScore;

    private String priorityRecommendation;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}