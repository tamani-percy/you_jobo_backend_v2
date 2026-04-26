package com.spring.job_tracker.helpers;

import com.spring.job_tracker.ai.helpers.JobAndDocumentAnalysisHelper;
import com.spring.job_tracker.dtos.requests.JobApplicationRequest;
import com.spring.job_tracker.dtos.responses.JobApplicationResponse;
import com.spring.job_tracker.models.Company;
import com.spring.job_tracker.models.JobApplication;
import com.spring.job_tracker.models.enums.*;
import com.spring.job_tracker.repositories.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JobApplicationHelper {

    private final WorkModeRepository workModeRepository;
    private final JobTypeRepository jobTypeRepository;
    private final SourceRepository sourceRepository;
    private final PriorityRepository priorityRepository;
    private final JobStatusRepository jobStatusRepository;
    private final CompanyHelper companyHelper;
    private final ContactPersonHelper contactPersonHelper;
    private final JobAndDocumentAnalysisHelper jobAndDocumentAnalysisHelper;

    public JobApplicationHelper(
            WorkModeRepository workModeRepository,
            JobTypeRepository jobTypeRepository,
            SourceRepository sourceRepository,
            PriorityRepository priorityRepository,
            JobStatusRepository jobStatusRepository,
            CompanyHelper companyHelper,
            ContactPersonHelper contactPersonHelper, JobAndDocumentAnalysisHelper jobAndDocumentAnalysisHelper) {
        this.workModeRepository = workModeRepository;
        this.jobTypeRepository = jobTypeRepository;
        this.sourceRepository = sourceRepository;
        this.priorityRepository = priorityRepository;
        this.jobStatusRepository = jobStatusRepository;
        this.companyHelper = companyHelper;
        this.contactPersonHelper = contactPersonHelper;
        this.jobAndDocumentAnalysisHelper = jobAndDocumentAnalysisHelper;
    }

    public JobApplication toEntity(JobApplicationRequest request, Company company) {
        JobApplication jobApplication = new JobApplication();
        applyToEntity(jobApplication, request, company);
        return jobApplication;
    }

    public void updateEntity(JobApplication existing, JobApplicationRequest request, Company company) {
        if (request.getTitle() != null) {
            existing.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }

        if (request.getDepartment() != null) {
            existing.setDepartment(request.getDepartment());
        }
        if (request.getSalaryMin() != null) {
            existing.setSalaryMin(request.getSalaryMin());
        }

        if (request.getSalaryMax() != null) {
            existing.setSalaryMax(request.getSalaryMax());
        }

        if (request.getCurrency() != null) {
            existing.setCurrency(request.getCurrency());
        }

        if (request.getJobPostUrl() != null) {
            existing.setJobPostUrl(request.getJobPostUrl());
        }

        if (request.getDatePosted() != null) {
            existing.setDatePosted(request.getDatePosted());
        }

        if (request.getDateApplied() != null) {
            existing.setDateApplied(request.getDateApplied());
        }

        if (request.getDeadline() != null) {
            existing.setDeadline(request.getDeadline());
        }

        if (request.getOutcomeReason() != null) {
            existing.setOutcomeReason(request.getOutcomeReason());
        }

        if (request.getCompany() != null) {
            existing.setCompany(company);
        }

        if (request.getJobType() != null) {
            existing.setJobType(
                    jobTypeRepository.findByJobType(EJobType.valueOf(request.getJobType()))
                            .orElseThrow(() -> new RuntimeException("Job type not found"))
            );
        }

        if (request.getSource() != null) {
            existing.setSource(
                    sourceRepository.findBySource(ESource.valueOf(request.getSource()))
                            .orElseThrow(() -> new RuntimeException("Source not found"))
            );
        }

        if (request.getPriority() != null) {
            existing.setPriority(
                    priorityRepository.findByPriority(EPriority.valueOf(request.getPriority()))
                            .orElseThrow(() -> new RuntimeException("Priority not found"))
            );
        }

        if (request.getWorkMode() != null) {
            existing.setWorkMode(
                    workModeRepository.findByWorkMode(EWorkMode.valueOf(request.getWorkMode()))
                            .orElseThrow(() -> new RuntimeException("Work mode not found"))
            );
        }

        if (request.getStatus() != null) {

            existing.setStatus(
                    jobStatusRepository.findByJobStatus(EJobStatus.valueOf(request.getStatus()))
                            .orElseThrow(() -> new RuntimeException("Job status not found"))
            );
        }

        existing.setUpdatedAt(LocalDateTime.now());

    }

    private void applyToEntity(JobApplication jobApplication, JobApplicationRequest request, Company company) {
        jobApplication.setTitle(request.getTitle());
        jobApplication.setDescription(request.getDescription());

        if (request.getDepartment() != null) {
            jobApplication.setDepartment(request.getDepartment());
        }
        if (jobApplication.getSalaryMin() != null) {
            jobApplication.setSalaryMin(request.getSalaryMin());
        }
        if (jobApplication.getSalaryMax() != null) {
            jobApplication.setSalaryMax(request.getSalaryMax());
        }
        if (jobApplication.getCurrency() != null) {
            jobApplication.setCurrency(request.getCurrency());
        }
        if (jobApplication.getJobPostUrl() != null) {
            jobApplication.setJobPostUrl(request.getJobPostUrl());
        }
        jobApplication.setJobPostUrl(request.getJobPostUrl());
        jobApplication.setDateApplied(request.getDateApplied());
        jobApplication.setDatePosted(request.getDatePosted());
        jobApplication.setDeadline(request.getDeadline());
        jobApplication.setOutcomeReason(request.getOutcomeReason());

        jobApplication.setCompany(company);
        jobApplication.setSource(
                sourceRepository.findBySource(ESource.valueOf(request.getSource()))
                        .orElseThrow(() -> new RuntimeException("Source not found"))
        );
        jobApplication.setPriority(
                priorityRepository.findByPriority(EPriority.valueOf(request.getPriority()))
                        .orElseThrow(() -> new RuntimeException("Priority not found"))
        );
        jobApplication.setWorkMode(
                workModeRepository.findByWorkMode(EWorkMode.valueOf(request.getWorkMode()))
                        .orElseThrow(() -> new RuntimeException("Work mode not found"))
        );
        jobApplication.setJobType(
                jobTypeRepository.findByJobType(EJobType.valueOf(request.getJobType()))
                        .orElseThrow(() -> new RuntimeException("Job type not found"))
        );
        jobApplication.setStatus(
                jobStatusRepository.findByJobStatus(EJobStatus.valueOf(request.getStatus()))
                        .orElseThrow(() -> new RuntimeException("Job status not found"))
        );
    }

    public JobApplicationResponse toResponse(JobApplication jobApplication) {
        JobApplicationResponse response = new JobApplicationResponse();
        response.setId(jobApplication.getId());
        response.setTitle(jobApplication.getTitle());
        response.setDepartment(jobApplication.getDepartment());
        response.setDescription(jobApplication.getDescription());
        response.setSalaryMin(jobApplication.getSalaryMin());
        response.setSalaryMax(jobApplication.getSalaryMax());
        response.setCurrency(jobApplication.getCurrency());
        response.setJobPostUrl(jobApplication.getJobPostUrl());
        response.setOutcomeReason(jobApplication.getOutcomeReason());
        response.setDateApplied(jobApplication.getDateApplied());
        response.setDatePosted(jobApplication.getDatePosted());
        response.setCreatedAt(jobApplication.getCreatedAt());
        response.setUpdatedAt(jobApplication.getUpdatedAt());
        response.setDeadline(jobApplication.getDeadline());
        response.setCompany(companyHelper.toResponse(jobApplication.getCompany()));
        response.setSource(jobApplication.getSource().getSource().name());
        response.setPriority(jobApplication.getPriority().getPriority().name());
        response.setWorkMode(jobApplication.getWorkMode().getWorkMode().name());
        response.setJobType(jobApplication.getJobType().getJobType().name());
        response.setStatus(jobApplication.getStatus().getJobStatus().name());
        return response;
    }
}