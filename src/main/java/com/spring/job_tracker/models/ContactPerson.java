package com.spring.job_tracker.models;

import com.spring.job_tracker.models.statuses.ContactPersonRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_persons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @ManyToOne
    @JoinColumn(name = "contact_person_role_id", nullable = false)
    private ContactPersonRole contactPersonRole;

    private String email;

    private String phone;

    private String linkedinUrl;

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