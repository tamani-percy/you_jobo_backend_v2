package com.spring.job_tracker.ai.controller;

import com.spring.job_tracker.ai.client.LlmClient;
import com.spring.job_tracker.ai.dto.requests.JobAndDocumentAnalysisRequest;
import com.spring.job_tracker.ai.dto.requests.JobPreInterviewAnalysisRequest;
import com.spring.job_tracker.ai.dto.responses.JobAndDocumentAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.NotesAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.InterviewPatternAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.PostInterviewAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.PreInterviewAnalysisResponse;
import com.spring.job_tracker.ai.services.AiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiService aiService;
    private final LlmClient llmClient;

    public AiController(AiService aiService, LlmClient llmClient) {
        this.aiService = aiService;
        this.llmClient = llmClient;
    }

    // SSE
    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> stream() {
        return llmClient.getEventStream()
                .map(status -> ServerSentEvent.<String>builder()
                        .data(status)
                        .build());
    }

    // JOB AND DOCUMENTS ANALYSIS
    @PostMapping("/job-and-document-analysis")
    public Mono<ResponseEntity<JobAndDocumentAnalysisResponse>> analyseJobAndDocument(@RequestBody JobAndDocumentAnalysisRequest jobAndDocumentAnalysisRequest) {
        return aiService.analyseJobAndDocument(jobAndDocumentAnalysisRequest).map(ResponseEntity::ok).onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build()));
    }

    @GetMapping("/job-and-document-analysis/all")
    public ResponseEntity<List<JobAndDocumentAnalysisResponse>> getAllJobAndDocumentsAiAnalyses() {
        return ResponseEntity.ok(aiService.getAllJobAndDocumentsAnalyses());
    }

    @GetMapping("/job-and-document-analysis")
    public ResponseEntity<List<JobAndDocumentAnalysisResponse>> getAllJobDocumentAiAnalysesByJob(@RequestParam("jobApplicationId") Long jobApplicationId) {
        return ResponseEntity.ok(aiService.getAllJobDocumentAnalysesByJob(jobApplicationId));
    }

    @DeleteMapping("/job-and-document-analysis")
    public ResponseEntity<Boolean> deleteJobAndDocumentAiAnalysis(@RequestParam("documentId") Long documentId) {
        return ResponseEntity.ok(aiService.deleteJobDocumentAnalysis(documentId));
    }

    // NOTES ANALYSIS
    @PostMapping("/job-notes-analysis")
    public Mono<ResponseEntity<NotesAnalysisResponse>> analyseJobNotes(@RequestParam("jobApplicationId") Long jobApplicationId) {
        return aiService.analyseJobNotes(jobApplicationId).map(ResponseEntity::ok).onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build()));
    }

    @GetMapping("/job-notes-analysis/all")
    public ResponseEntity<List<NotesAnalysisResponse>> getAllJobNotesAnalyses() {
        return ResponseEntity.ok(aiService.getAllJobNotesAnalyses());
    }

    @GetMapping("/job-notes-analysis/job-application")
    public ResponseEntity<NotesAnalysisResponse> getJobNotesAnalysisByJobApplication(@RequestParam("jobApplicationId") Long jobApplicationId) {
        return ResponseEntity.ok(aiService.getNotesAnalysisByJobApplication(jobApplicationId));
    }

    @DeleteMapping("/job-notes-analysis")
    public ResponseEntity<Boolean> deleteJobNotesAnalysis(@RequestParam("jobNotesAnalysisId") Long jobNotesAnalysisId) {
        return ResponseEntity.ok(aiService.deleteJobNotesAnalysis(jobNotesAnalysisId));
    }

    // INTERVIEW ANALYSIS
    @PostMapping("job-pre-interview-analysis")
    public Mono<ResponseEntity<PreInterviewAnalysisResponse>> analyseJobPreInterview(@RequestBody JobPreInterviewAnalysisRequest jobPreInterviewAnalysisRequest) {
        return aiService.analysePreInterview(jobPreInterviewAnalysisRequest).map(ResponseEntity::ok).onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build()));
    }

    @PostMapping("job-interview-pattern-analysis")
    public Mono<ResponseEntity<InterviewPatternAnalysisResponse>> analyseInterviewPattern(@RequestParam("jobApplicationId") Long jobApplicationId) {
        return aiService.analyseInterviewPattern(jobApplicationId).map(ResponseEntity::ok).onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build()));
    }

    @PostMapping("job-post-interview-analysis")
    public Mono<ResponseEntity<PostInterviewAnalysisResponse>> analysePostInterview(@RequestParam("jobApplicationId") Long jobApplicationId) {
        return aiService.analysePostInterview(jobApplicationId).map(ResponseEntity::ok).onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build()));
    }

    @GetMapping("job-pre-interview-analysis/all")
    public ResponseEntity<List<PreInterviewAnalysisResponse>> getAllJobPreInterviewAnalysis() {
        return ResponseEntity.ok(aiService.getAllPreInterviewAnalyses());
    }

    @GetMapping("job-interview-pattern-analysis/all")
    public ResponseEntity<List<InterviewPatternAnalysisResponse>> getAllJobInterviewPatternAnalysis() {
        return ResponseEntity.ok(aiService.getAllInterviewPatternAnalyses());
    }

    @GetMapping("job-post-interview-analysis/all")
    public ResponseEntity<List<PostInterviewAnalysisResponse>> getAllJobPostInterviewAnalysis() {
        return ResponseEntity.ok(aiService.getAllPostInterviewAnalyses());
    }

    @GetMapping("job-pre-interview-analysis/job-application")
    public ResponseEntity<PreInterviewAnalysisResponse> getPreInterviewAnalysisByJobApplication(@RequestParam("jobApplicationId") Long jobApplicationId) {
        return ResponseEntity.ok(aiService.getPreInterviewAnalysisByJobApplication(jobApplicationId));
    }

    @GetMapping("job-interview-pattern-analysis/job-application")
    public ResponseEntity<InterviewPatternAnalysisResponse> getInterviewPatternAnalysisByJobApplication(@RequestParam("jobApplicationId") Long jobApplicationId) {
        return ResponseEntity.ok(aiService.getInterviewPatternAnalysisByJobApplication(jobApplicationId));
    }

    @GetMapping("job-post-interview-analysis/job-application")
    public ResponseEntity<PostInterviewAnalysisResponse> getPostInterviewAnalysisByJobApplication(@RequestParam("jobApplicationId") Long jobApplicationId) {
        return ResponseEntity.ok(aiService.getPostInterviewAnalysisByJobApplication(jobApplicationId));
    }

    @DeleteMapping("job-pre-interview-analysis/{preInterviewAnalysisId}")
    public ResponseEntity<Boolean> deletePreInterviewAnalysis(@PathVariable("preInterviewAnalysisId") Long preInterviewAnalysisId) {
        return ResponseEntity.ok(aiService.deletePreInterviewAnalysis(preInterviewAnalysisId));
    }

    @DeleteMapping("job-post-interview-analysis/{postInterviewAnalysisId}")
    public ResponseEntity<Boolean> deletePostInterviewAnalysis(@PathVariable("postInterviewAnalysisId") Long postInterviewAnalysisId) {
        return ResponseEntity.ok(aiService.deletePostInterviewAnalysis(postInterviewAnalysisId));
    }

    @DeleteMapping("job-interview-pattern-analysis/{interviewPatternAnalysisId}")
    public ResponseEntity<Boolean> deleteInterviewPatternAnalysis(@PathVariable("interviewPatternAnalysisId") Long interviewPatternAnalysisId) {
        return ResponseEntity.ok(aiService.deleteInterviewPatternAnalysis(interviewPatternAnalysisId));
    }
}