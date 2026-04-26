package com.spring.job_tracker.models;

import com.spring.job_tracker.ai.models.JobAndDocumentAnalysis;
import com.spring.job_tracker.models.statuses.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String department;

    @ManyToOne
    @JoinColumn(name = "job_type_id", nullable = false)
    private JobType jobType;

    @ManyToOne
    @JoinColumn(name = "work_mode_id", nullable = false)
    private WorkMode workMode;

    private String location;

    private BigDecimal salaryMin;

    private BigDecimal salaryMax;

    private String currency;

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    private String jobPostUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "job_status_id", nullable = false)
    private JobStatus status;

    private LocalDate datePosted;

    private LocalDate dateApplied;

    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "priority_id", nullable = false)
    private Priority priority;

    @Column(columnDefinition = "TEXT")
    private String outcomeReason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interview> interviews;

    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobAndDocumentAnalysis> jobAndDocumentAnalyses;

    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactPerson> contacts;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}