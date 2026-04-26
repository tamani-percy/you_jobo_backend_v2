package com.spring.job_tracker.controllers;

import com.spring.job_tracker.dtos.requests.InterviewRequest;
import com.spring.job_tracker.dtos.responses.InterviewResponse;
import com.spring.job_tracker.services.InterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<InterviewResponse> createInterview(@RequestBody InterviewRequest interviewRequest) {
        return ResponseEntity.ok(interviewService.createInterview(interviewRequest));
    }

    @GetMapping("/most-recents")
    public ResponseEntity<List<InterviewResponse>> getMostRecentInterviews() {
        return ResponseEntity.ok(interviewService.getMostRecentInterviews());
    }

    @GetMapping("/all")
    public ResponseEntity<List<InterviewResponse>> getAllInterviews() {
        return ResponseEntity.ok(interviewService.getAllInterviews());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<InterviewResponse> getInterviewById(@PathVariable Long id) {
        return ResponseEntity.ok(interviewService.getInterviewById(id));
    }

    @PatchMapping
    public ResponseEntity<InterviewResponse> updateInterview(@RequestBody InterviewRequest interviewRequest) {
        return ResponseEntity.ok(interviewService.updateInterviewById(interviewRequest));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Boolean> deleteInterview(@PathVariable Long id) {
        return ResponseEntity.ok(interviewService.deleteInterviewById(id));
    }

    @GetMapping("/job-application/{id:\\d+}")
    public ResponseEntity<List<InterviewResponse>> getInterviewsByJobApplication(@PathVariable Long id) {
        return ResponseEntity.ok(interviewService.getAllInterviewsByJobApplication(id));
    }

    @GetMapping("/interview-types")
    public ResponseEntity<List<InterviewResponse>> getAllInterviewsByInterviewTypes(@RequestParam("interviewType") String interviewType) {
        return ResponseEntity.ok(interviewService.getAllInterviewsByInterviewType(interviewType));
    }

    @GetMapping("/interview-stages")
    public ResponseEntity<List<InterviewResponse>> getAllInterviewsByInterviewStages(@RequestParam("interviewStage") String interviewStage) {
        return ResponseEntity.ok(interviewService.getAllInterviewsByInterviewStage(interviewStage));
    }

    @GetMapping("/interview-results")
    public ResponseEntity<List<InterviewResponse>> getAllInterviewsByInterviewResults(@RequestParam("interviewResults") String interviewResult) {
        return ResponseEntity.ok(interviewService.getAllInterviewsByInterviewResult(interviewResult));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getInterviewCount() {
        return ResponseEntity.ok(interviewService.countAllInterviews());
    }
}
