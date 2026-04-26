package com.spring.job_tracker.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationRequest {
    private Long id;
    private String title;
    private String description;
    private String department;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String currency;
    private String jobPostUrl;
    private LocalDate datePosted;
    private LocalDate dateApplied;
    private LocalDate deadline;
    private String outcomeReason;
    private CompanyRequest company;

    // ENUMS
    private String priority;
    private String workMode;
    private String jobType;
    private String source;
    private String status;
}
