package com.spring.job_tracker.controllers;

import com.spring.job_tracker.dtos.requests.JobApplicationRequest;
import com.spring.job_tracker.dtos.responses.JobApplicationFocusResponse;
import com.spring.job_tracker.dtos.responses.JobApplicationResponse;
import com.spring.job_tracker.services.JobApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping("")
    public ResponseEntity<JobApplicationResponse> createJobApplication(@RequestBody JobApplicationRequest jobApplicationRequest) {
        return ResponseEntity.ok(jobApplicationService.createJobApplication(jobApplicationRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> getJobApplication(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.getJobApplication(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobApplicationResponse>> getAllJobApplications() {
        return ResponseEntity.ok(jobApplicationService.getAllJobApplications());
    }

    @PatchMapping("")
    public ResponseEntity<JobApplicationResponse> updateJobApplication(@RequestBody JobApplicationRequest jobApplicationRequest) {
        return ResponseEntity.ok(jobApplicationService.updateJobApplication(jobApplicationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteJobApplication(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.deleteJobApplication(id));
    }

    @PostMapping("/status")
    public ResponseEntity<JobApplicationResponse> changeJobApplicationStatus(@RequestParam("jobApplicationId") Long id, @RequestParam("status") String status) {
        return ResponseEntity.ok(jobApplicationService.changeJobApplicationStatus(id, status));
    }

    // DAILY FOCUS
    @GetMapping("/daily-focus")
    public ResponseEntity<List<JobApplicationFocusResponse>> getDailyFocus() {
        return ResponseEntity.ok(jobApplicationService.getDailyFocus());
    }

    //STATISTICS
    @GetMapping("/count")
    public ResponseEntity<Long> countJobApplications() {
        return ResponseEntity.ok(jobApplicationService.countJobApplications());
    }

    @GetMapping("/count/status")
    public ResponseEntity<Long> countJobApplicationsByJobStatus(@RequestParam("status") String status) {
        return ResponseEntity.ok(jobApplicationService.countJobApplicationsByJobStatus(status));
    }

    @GetMapping("/count/status/active")
    public ResponseEntity<Long> countJobApplicationsByJobStatusActive() {
        return ResponseEntity.ok(jobApplicationService.countAllActiveJobApplications());
    }

    @GetMapping("/count/interview")
    public ResponseEntity<Long> countJobApplicationsByJobStatusInterview() {
        return ResponseEntity.ok(jobApplicationService.countAllInterviewJobApplications());
    }

    @GetMapping("/count/offers")
    public ResponseEntity<Long> countJobApplicationsByJobStatusOffers() {
        return ResponseEntity.ok(jobApplicationService.countAllOffersJobApplications());
    }

    @GetMapping("/count/accepted")
    public ResponseEntity<Long> countJobApplicationsByJobStatusAccepted() {
        return ResponseEntity.ok(jobApplicationService.countAllAcceptedJobApplications());
    }

    @GetMapping("/rate/interview")
    public ResponseEntity<Double> getJobApplicationsInterviewRate() {
        return ResponseEntity.ok(jobApplicationService.getJobApplicationsInterviewRate());
    }

    @GetMapping("/rate/response")
    public ResponseEntity<Double> getJobApplicationsResponseRate() {
        return ResponseEntity.ok(jobApplicationService.getJobApplicationsResponseRate());
    }

    @GetMapping("/rate/offer")
    public ResponseEntity<Double> getJobApplicationsOfferRate() {
        return ResponseEntity.ok(jobApplicationService.getJobApplicationsOfferRate());
    }

    @GetMapping("/rate/success")
    public ResponseEntity<Double> getJobApplicationsSuccessRate() {
        return ResponseEntity.ok(jobApplicationService.getJobApplicationsSuccessRate());
    }

    @GetMapping("/statistics/job-status-count")
    public ResponseEntity<Map<String, Long>> getJobApplicationsJobStatusCount() {
        return ResponseEntity.ok(jobApplicationService.getJobStatusCounts());
    }

    @GetMapping("/oldest")
    public ResponseEntity<List<JobApplicationResponse>> getOldestJobApplications() {
        return ResponseEntity.ok(jobApplicationService.getOldestJobApplications());
    }
}
