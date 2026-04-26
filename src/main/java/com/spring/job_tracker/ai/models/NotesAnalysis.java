package com.spring.job_tracker.ai.models;

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
        name = "notes_analyses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"job_application_id", "id"})
        }
)
public class NotesAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @OneToOne
    @JoinColumn(name = "job_application_id", nullable = false, unique = true)
    private JobApplication jobApplication;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "notes_strengths_analysis",
            joinColumns = @JoinColumn(name = "note_analyses_id")
    )
    @Column(name = "strength")
    @OrderColumn(name = "position")
    private List<String> strengths;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "notes_concerns_analysis",
            joinColumns = @JoinColumn(name = "note_analyses_id")
    )
    @Column(name = "concern")
    @OrderColumn(name = "position")
    private List<String> concerns;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "notes_next_steps_analysis",
            joinColumns = @JoinColumn(name = "note_analyses_id")
    )
    @Column(name = "next_step")
    @OrderColumn(name = "position")
    private List<String> nextSteps;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "notes_patterns_analysis",
            joinColumns = @JoinColumn(name = "note_analyses_id")
    )
    @Column(name = "pattern")
    @OrderColumn(name = "next_step")
    private List<String> patterns;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
