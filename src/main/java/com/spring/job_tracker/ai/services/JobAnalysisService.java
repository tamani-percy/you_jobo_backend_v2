package com.spring.job_tracker.ai.services;

import com.spring.job_tracker.ai.client.LlmClient;
import com.spring.job_tracker.ai.dto.requests.JobAndDocumentAnalysisRequest;
import com.spring.job_tracker.ai.dto.responses.JobAndDocumentAnalysisResponse;
import com.spring.job_tracker.ai.helpers.JobAndDocumentAnalysisHelper;
import com.spring.job_tracker.ai.models.JobAndDocumentAnalysis;
import com.spring.job_tracker.ai.models.estatuses.ESinkEventStatuses;
import com.spring.job_tracker.ai.parser.AiResponseParser;
import com.spring.job_tracker.ai.prompts.JobAnalysisPromptBuilder;
import com.spring.job_tracker.ai.repositories.JobAndDocumentAnalysisRepository;
import com.spring.job_tracker.models.Company;
import com.spring.job_tracker.models.Document;
import com.spring.job_tracker.models.JobApplication;
import com.spring.job_tracker.models.Note;
import com.spring.job_tracker.repositories.DocumentRepository;
import com.spring.job_tracker.repositories.JobApplicationRepository;
import com.spring.job_tracker.repositories.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobAnalysisService {

    private final JobApplicationRepository jobApplicationRepository;
    private final DocumentRepository documentRepository;
    private final NoteRepository noteRepository;
    private final ResumeTextExtractorService resumeTextExtractorService;
    private final LlmClient llmClient;
    private final AiResponseParser aiResponseParser;
    private final JobAndDocumentAnalysisRepository jobAndDocumentAnalysisRepository;
    private final JobAndDocumentAnalysisHelper jobAndDocumentAnalysisHelper;
    private final Scheduler jdbcScheduler = Schedulers.boundedElastic();

    public JobAnalysisService(
            JobApplicationRepository jobApplicationRepository,
            DocumentRepository documentRepository,
            NoteRepository noteRepository,
            ResumeTextExtractorService resumeTextExtractorService,
            LlmClient llmClient,
            AiResponseParser aiResponseParser,
            JobAndDocumentAnalysisRepository jobAndDocumentAnalysisRepository, JobAndDocumentAnalysisHelper jobAndDocumentAnalysisHelper) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.documentRepository = documentRepository;
        this.noteRepository = noteRepository;
        this.resumeTextExtractorService = resumeTextExtractorService;
        this.llmClient = llmClient;
        this.aiResponseParser = aiResponseParser;
        this.jobAndDocumentAnalysisRepository = jobAndDocumentAnalysisRepository;
        this.jobAndDocumentAnalysisHelper = jobAndDocumentAnalysisHelper;
    }

    @Transactional
    public Mono<JobAndDocumentAnalysisResponse> analyseJobAndDocument(JobAndDocumentAnalysisRequest jobAndDocumentAnalysisRequest) {
        return Mono.fromCallable(() -> doAnalyseJobAndDocument(jobAndDocumentAnalysisRequest))
                .subscribeOn(jdbcScheduler);
    }

    public JobAndDocumentAnalysisResponse doAnalyseJobAndDocument(JobAndDocumentAnalysisRequest jobAndDocumentAnalysisRequest) {
        JobApplication job = jobApplicationRepository.findById(jobAndDocumentAnalysisRequest.getJobApplicationId())
                .orElseThrow(() -> new RuntimeException("Job application not found"));

        Document resumeDoc = documentRepository.findByIdAndJobApplicationId(jobAndDocumentAnalysisRequest.getDocumentId(), jobAndDocumentAnalysisRequest.getJobApplicationId())
                .orElseThrow(() -> new RuntimeException("Document not found for this job application"));

        String resumeText = resumeTextExtractorService.extractText(resumeDoc);

        List<Note> notes = noteRepository.findByJobApplicationId(jobAndDocumentAnalysisRequest.getJobApplicationId()).orElseThrow(() -> new RuntimeException("Notes not found"));
        ;
        String notesText = notes.stream()
                .map(Note::getContent)
                .filter(content -> content != null && !content.isBlank())
                .collect(Collectors.joining("\n"));

        Company company = job.getCompany();

        String prompt = JobAnalysisPromptBuilder.build(
                job.getTitle(),
                company != null ? company.getName() : "",
                job.getDescription(),
                resumeText,
                notesText
        );

        String rawResponse = llmClient.generate(prompt, ESinkEventStatuses.JOB_AND_DOCUMENT_ANALYSIS_COMPLETE.name(), ESinkEventStatuses.JOB_AND_DOCUMENT_ANALYSIS_ERROR.name()).block();
        JobAndDocumentAnalysisResponse analysis = aiResponseParser.parseJobAndDocumentAnalysis(rawResponse);


        jobAndDocumentAnalysisRepository.findByDocumentId(jobAndDocumentAnalysisRequest.getDocumentId())
                .ifPresent(jobAndDocumentAnalysisRepository::delete);

        JobAndDocumentAnalysis entity = jobAndDocumentAnalysisHelper.toEntity(analysis, job);
        entity.setDocument(resumeDoc);
        entity.setJobApplication(job);

        JobAndDocumentAnalysis saved = jobAndDocumentAnalysisRepository.save(entity);
        return jobAndDocumentAnalysisHelper.toResponse(saved);
    }

    public List<JobAndDocumentAnalysisResponse> getAllJobDocumentAnalyses() {
        List<JobAndDocumentAnalysis> jobsAndDocuments = (List<JobAndDocumentAnalysis>) jobAndDocumentAnalysisRepository.findAll();
        return jobsAndDocuments.stream().map(jobAndDocumentAnalysisHelper::toResponse).toList();
    }

    public List<JobAndDocumentAnalysisResponse> getAllJobDocumentAnalysesByJob(Long jobId) {
        if (jobAndDocumentAnalysisRepository.findByJobApplicationId(jobId).isPresent()) {
            List<JobAndDocumentAnalysis> jobs = jobAndDocumentAnalysisRepository.findByJobApplicationId(jobId).get();
            return jobs.stream().map(jobAndDocumentAnalysisHelper::toResponse).toList();
        }
        return null;
    }

    public Boolean deleteJobDocumentAnalysis(Long documentId) {
        if (jobAndDocumentAnalysisRepository.findByDocumentId(documentId).isPresent()) {
            jobAndDocumentAnalysisRepository.deleteByDocumentId(documentId);
            return true;
        }
        return false;
    }
}