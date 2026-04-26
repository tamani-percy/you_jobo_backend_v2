package com.spring.job_tracker.services;

import com.spring.job_tracker.ai.models.JobAndDocumentAnalysis;
import com.spring.job_tracker.ai.repositories.JobAndDocumentAnalysisRepository;
import com.spring.job_tracker.dtos.projections.JobStatusCount;
import com.spring.job_tracker.dtos.requests.CompanyRequest;
import com.spring.job_tracker.dtos.requests.JobApplicationRequest;
import com.spring.job_tracker.dtos.responses.JobApplicationFocusResponse;
import com.spring.job_tracker.dtos.responses.JobApplicationResponse;
import com.spring.job_tracker.helpers.CompanyHelper;
import com.spring.job_tracker.helpers.JobApplicationHelper;
import com.spring.job_tracker.models.Company;
import com.spring.job_tracker.models.JobApplication;
import com.spring.job_tracker.models.enums.EJobStatus;
import com.spring.job_tracker.models.statuses.JobStatus;
import com.spring.job_tracker.models.statuses.Priority;
import com.spring.job_tracker.repositories.CompanyRepository;
import com.spring.job_tracker.repositories.JobApplicationRepository;
import com.spring.job_tracker.repositories.JobStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.spring.job_tracker.models.enums.EJobStatus.REJECTED;
import static com.spring.job_tracker.models.enums.EJobStatus.WITHDRAWN;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final CompanyRepository companyRepository;
    private final JobApplicationHelper jobApplicationHelper;
    private final CompanyHelper companyHelper;
    private final JobStatusRepository jobStatusRepository;
    private final JobAndDocumentAnalysisRepository jobAndDocumentAnalysisRepository;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository, CompanyRepository companyRepository, JobApplicationHelper jobApplicationHelper, CompanyHelper companyHelper, JobStatusRepository jobStatusRepository, JobAndDocumentAnalysisRepository jobAndDocumentAnalysisRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.companyRepository = companyRepository;
        this.jobApplicationHelper = jobApplicationHelper;
        this.companyHelper = companyHelper;
        this.jobStatusRepository = jobStatusRepository;
        this.jobAndDocumentAnalysisRepository = jobAndDocumentAnalysisRepository;
    }

    @Transactional
    public JobApplicationResponse createJobApplication(JobApplicationRequest request) {
        Company company = resolveCompany(request.getCompany());
        JobApplication jobApplication = jobApplicationHelper.toEntity(request, company);
        JobApplication saved = jobApplicationRepository.save(jobApplication);
        return jobApplicationHelper.toResponse(saved);
    }

    @Transactional
    public JobApplicationResponse updateJobApplication(JobApplicationRequest request) {
        if (request.getId() == null) {
            throw new IllegalArgumentException("Job application ID is required for update");
        }

        JobApplication existing = jobApplicationRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Job application not found"));

        Company company = null;
        if (request.getCompany() != null) {
            company = resolveCompany(request.getCompany());
        }

        jobApplicationHelper.updateEntity(existing, request, company);

        JobApplication saved = jobApplicationRepository.save(existing);
        return jobApplicationHelper.toResponse(saved);
    }


    public List<JobApplicationResponse> getAllJobApplications() {
        return jobApplicationRepository.findAll().stream().map(jobApplicationHelper::toResponse).toList();

    }

    public JobApplicationResponse getJobApplication(Long id) {
        if (jobApplicationRepository.findById(id).isPresent()) {
            return jobApplicationHelper.toResponse(jobApplicationRepository.findById(id).get());
        } else {
            throw new RuntimeException("Job application not found");
        }
    }

    public boolean deleteJobApplication(Long id) {
        if (jobApplicationRepository.findById(id).isPresent()) {
            jobApplicationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public JobApplicationResponse changeJobApplicationStatus(Long id, String status) {
        if (jobApplicationRepository.findById(id).isPresent()) {
            JobApplication jobApplication = jobApplicationRepository.findById(id).get();
            JobStatus jobStatus = jobStatusRepository.findByJobStatus(EJobStatus.valueOf(status)).orElseThrow(() -> new RuntimeException("Job status not found"));
            jobApplication.setStatus(jobStatus);
            JobApplication saved = jobApplicationRepository.save(jobApplication);
            return jobApplicationHelper.toResponse(saved);
        }
        throw new RuntimeException("Job application not found");
    }

    private Company resolveCompany(CompanyRequest companyRequest) {
        if (companyRequest == null) {
            throw new IllegalArgumentException("Company is required");
        }

        if (companyRequest.getId() != null) {
            return companyRepository.findById(companyRequest.getId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));
        }

        return companyRepository.save(companyHelper.toEntity(companyRequest));
    }

    // DAILY FOCUS
    public List<JobApplicationFocusResponse> getDailyFocus() {
        JobStatus rejected = jobStatusRepository.findByJobStatus(REJECTED)
                .orElseThrow(() -> new RuntimeException("Job Status REJECTED not found"));

        JobStatus withdrawn = jobStatusRepository.findByJobStatus(WITHDRAWN)
                .orElseThrow(() -> new RuntimeException("Job Status WITHDRAWN not found"));

        List<JobApplication> jobs = jobApplicationRepository.findByStatusNotIn(List.of(rejected, withdrawn));

        return jobs.stream()
                .map(job -> new JobApplicationFocusResponse(
                        jobApplicationHelper.toResponse(job),
                        calculateFocusScore(job)
                ))
                .sorted(Comparator.comparing(JobApplicationFocusResponse::getScore).reversed())
                .limit(3)
                .toList();
    }

    // STATISTICS
    public Long countJobApplications() {
        return jobApplicationRepository.count();
    }

    public Long countJobApplicationsByJobStatus(String jobStatus) {
        JobStatus existingJobStatus = jobStatusRepository.findByJobStatus(EJobStatus.valueOf(jobStatus)).orElseThrow(() -> new RuntimeException("Job status not found"));
        return jobApplicationRepository.countAllByStatus(existingJobStatus);
    }

    public Long countAllActiveJobApplications() {
        return jobApplicationRepository.countByStatuses(List.of(EJobStatus.APPLIED, EJobStatus.UNDER_REVIEW, EJobStatus.SHORTLISTED, EJobStatus.INTERVIEW_SCHEDULED, EJobStatus.INTERVIEWED));
    }

    public Long countAllInterviewJobApplications() {
        return jobApplicationRepository.countByStatuses(List.of(EJobStatus.INTERVIEW_SCHEDULED, EJobStatus.INTERVIEWED));
    }


    public Long countAllOffersJobApplications() {
        return jobApplicationRepository.countByStatuses(List.of(EJobStatus.INTERVIEW_SCHEDULED, EJobStatus.INTERVIEWED));
    }

    public Long countAllAcceptedJobApplications() {
        return jobApplicationRepository.countByStatuses(List.of(EJobStatus.INTERVIEW_SCHEDULED, EJobStatus.INTERVIEWED));
    }

    public Double getJobApplicationsInterviewRate() {
        long activeCount = jobApplicationRepository.countByStatuses(List.of(EJobStatus.INTERVIEW_SCHEDULED, EJobStatus.INTERVIEWED));
        long appliedCount = jobApplicationRepository.countByStatuses(List.of(EJobStatus.APPLIED));
        if (appliedCount == 0) return 0.0;
        return (double) activeCount / appliedCount;
    }

    public Double getJobApplicationsResponseRate() {
        long activeCount = jobApplicationRepository.countByStatuses(List.of(EJobStatus.UNDER_REVIEW, EJobStatus.SHORTLISTED, EJobStatus.INTERVIEW_SCHEDULED, EJobStatus.INTERVIEWED, EJobStatus.OFFER_RECEIVED, EJobStatus.ACCEPTED));
        long appliedCount = jobApplicationRepository.countByStatuses(List.of(EJobStatus.APPLIED));
        if (appliedCount == 0) return 0.0;
        return (double) activeCount / appliedCount;
    }

    public Double getJobApplicationsOfferRate() {
        long activeCount = jobApplicationRepository.countByStatuses(List.of(EJobStatus.OFFER_RECEIVED));
        long appliedCount = jobApplicationRepository.countByStatuses(List.of(EJobStatus.APPLIED));
        if (appliedCount == 0) return 0.0;
        return (double) activeCount / appliedCount;
    }

    public Double getJobApplicationsSuccessRate() {
        long activeCount = jobApplicationRepository.countByStatuses(List.of(EJobStatus.ACCEPTED));
        long appliedCount = jobApplicationRepository.countByStatuses(List.of(EJobStatus.APPLIED));
        if (appliedCount == 0) return 0.0;
        return (double) activeCount / appliedCount;
    }

    public Map<String, Long> getJobStatusCounts() {
        return jobApplicationRepository.countGroupedByStatus()
                .stream()
                .collect(Collectors.toMap(JobStatusCount::getStatus, JobStatusCount::getCount));
    }

    public List<JobApplicationResponse> getOldestJobApplications() {
        List<EJobStatus> excludedStatuses = List.of(
                EJobStatus.ACCEPTED,
                REJECTED,
                WITHDRAWN
        );
        return jobApplicationRepository.findTop5ByStatus_JobStatusNotInOrderByUpdatedAtAsc(excludedStatuses).orElseThrow(() -> new RuntimeException("No job applications exist")).stream().map(jobApplicationHelper::toResponse).toList();
    }


    private double calculateFocusScore(JobApplication job) {
        List<JobAndDocumentAnalysis> analyses = jobAndDocumentAnalysisRepository
                .findByJobApplicationId(job.getId())
                .orElse(List.of());

        double match = analyses.stream()
                .max(Comparator.comparing(
                        JobAndDocumentAnalysis::getCreatedAt,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .map(JobAndDocumentAnalysis::getMatchScore)
                .orElse(0);

        double priority = mapPriority(job.getPriority());
        double recency = calculateRecencyScore(job.getDateApplied());
        double status = mapStatus(job.getStatus());

        return (priority * 0.4)
                + (match * 0.4)
                + (recency * 0.1)
                + (status * 0.1);
    }

    private double calculateRecencyScore(LocalDate dateApplied) {
        if (dateApplied == null) {
            return 0.0;
        }

        long daysOld = java.time.temporal.ChronoUnit.DAYS.between(dateApplied, LocalDate.now());

        if (daysOld <= 0) return 100.0;
        if (daysOld >= 100) return 0.0;

        return 100.0 - daysOld;
    }

    private double mapPriority(Priority priority) {
        if (priority == null || priority.getPriority() == null) {
            return 0.0;
        }

        return switch (priority.getPriority()) {
            case HIGH -> 100.0;
            case MEDIUM -> 70.0;
            case LOW -> 40.0;
        };
    }

    private double mapStatus(JobStatus status) {
        if (status == null || status.getJobStatus() == null) {
            return 0.0;
        }

        return switch (status.getJobStatus()) {
            case INTERVIEW_SCHEDULED -> 100.0;
            case SHORTLISTED -> 90.0;
            case UNDER_REVIEW -> 80.0;
            case APPLIED -> 70.0;
            case DRAFT -> 30.0;
            case INTERVIEWED -> 60.0;
            case OFFER_RECEIVED -> 95.0;
            case ACCEPTED, REJECTED, WITHDRAWN -> 0.0;
        };
    }
}
