package com.spring.job_tracker.ai.services;

import com.spring.job_tracker.ai.dto.requests.JobAndDocumentAnalysisRequest;
import com.spring.job_tracker.ai.dto.requests.JobPreInterviewAnalysisRequest;
import com.spring.job_tracker.ai.dto.responses.JobAndDocumentAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.NotesAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.InterviewPatternAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.PreInterviewAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.PostInterviewAnalysisResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AiService {

    private final JobAnalysisService jobAnalysisService;
    private final NotesAnalysisService notesAnalysisService;
    private final InterviewsAnalysisService interviewsAnalysisService;

    public AiService(JobAnalysisService jobAnalysisService, NotesAnalysisService notesAnalysisService, InterviewsAnalysisService interviewsAnalysisService) {
        this.jobAnalysisService = jobAnalysisService;
        this.notesAnalysisService = notesAnalysisService;
        this.interviewsAnalysisService = interviewsAnalysisService;
    }

    // JOB AND DOCUMENT ANALYSIS
    public Mono<JobAndDocumentAnalysisResponse> analyseJobAndDocument(JobAndDocumentAnalysisRequest jobAndDocumentAnalysisRequest) {
        return jobAnalysisService.analyseJobAndDocument(jobAndDocumentAnalysisRequest);
    }

    public List<JobAndDocumentAnalysisResponse> getAllJobAndDocumentsAnalyses() {
        return jobAnalysisService.getAllJobDocumentAnalyses();
    }

    public List<JobAndDocumentAnalysisResponse> getAllJobDocumentAnalysesByJob(Long jobApplicationId) {
        return jobAnalysisService.getAllJobDocumentAnalysesByJob(jobApplicationId);
    }

    public Boolean deleteJobDocumentAnalysis(Long documentId) {
        return jobAnalysisService.deleteJobDocumentAnalysis(documentId);
    }

    // NOTES ANALYSIS
    public List<NotesAnalysisResponse> getAllJobNotesAnalyses() {
        return notesAnalysisService.getJobNotesAnalyses();
    }

    public NotesAnalysisResponse getNotesAnalysisByJobApplication(Long jobApplicationId) {
        return notesAnalysisService.getNotesAnalysisByJobApplication(jobApplicationId);
    }

    public Mono<NotesAnalysisResponse> analyseJobNotes(Long jobApplicationId) {
        return notesAnalysisService.analyseJobAndDocument(jobApplicationId);
    }

    public Boolean deleteJobNotesAnalysis(Long jobNoteAnalysisId) {
        return notesAnalysisService.deleteNoteAnalysis(jobNoteAnalysisId);
    }

    // INTERVIEWS
    public Mono<PostInterviewAnalysisResponse> analysePostInterview(Long jobApplicationId) {
        return interviewsAnalysisService.analysePostInterview(jobApplicationId);
    }

    public Mono<PreInterviewAnalysisResponse> analysePreInterview(JobPreInterviewAnalysisRequest jobPreInterviewAnalysisRequest) {
        return interviewsAnalysisService.analysePreInterview(jobPreInterviewAnalysisRequest);
    }

    public Mono<InterviewPatternAnalysisResponse> analyseInterviewPattern(Long jobApplicationId) {
        return interviewsAnalysisService.analyseInterviewPattern(jobApplicationId);
    }

    public PostInterviewAnalysisResponse getPostInterviewAnalysisByJobApplication(Long jobApplicationId) {
        return interviewsAnalysisService.getPostInterviewAnalysisByJobApplicationId(jobApplicationId);
    }

    public InterviewPatternAnalysisResponse getInterviewPatternAnalysisByJobApplication(Long jobApplicationId) {
        return interviewsAnalysisService.getInterviewPatternAnalysisByJobApplicationId(jobApplicationId);
    }

    public PreInterviewAnalysisResponse getPreInterviewAnalysisByJobApplication(Long jobApplicationId) {
        return interviewsAnalysisService.getInterviewPrepAnalysisByJobApplicationId(jobApplicationId);
    }

    public List<PostInterviewAnalysisResponse> getAllPostInterviewAnalyses() {
        return interviewsAnalysisService.getAllPostInterviewAnalyses();
    }

    public List<InterviewPatternAnalysisResponse> getAllInterviewPatternAnalyses() {
        return interviewsAnalysisService.getAllInterviewPatternAnalyses();
    }

    public List<PreInterviewAnalysisResponse> getAllPreInterviewAnalyses() {
        return interviewsAnalysisService.getAllPreInterviewAnalyses();
    }

    public Boolean deletePreInterviewAnalysis(Long preInterviewAnalysisId){
        return interviewsAnalysisService.deletePreInterviewAnalysis(preInterviewAnalysisId);
    }

    public Boolean deletePostInterviewAnalysis(Long postInterviewAnalysisId){
        return interviewsAnalysisService.deletePostInterviewAnalysis(postInterviewAnalysisId);
    }

    public Boolean deleteInterviewPatternAnalysis(Long interviewPatternAnalysisId){
        return interviewsAnalysisService.deleteInterviewPatternAnalysis(interviewPatternAnalysisId);
    }
}