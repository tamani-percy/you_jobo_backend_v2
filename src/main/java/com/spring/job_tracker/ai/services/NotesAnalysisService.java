package com.spring.job_tracker.ai.services;

import com.spring.job_tracker.ai.client.LlmClient;
import com.spring.job_tracker.ai.dto.responses.NotesAnalysisResponse;
import com.spring.job_tracker.ai.helpers.NotesAnalysisHelper;
import com.spring.job_tracker.ai.models.NotesAnalysis;
import com.spring.job_tracker.ai.models.estatuses.ESinkEventStatuses;
import com.spring.job_tracker.ai.parser.AiResponseParser;
import com.spring.job_tracker.ai.prompts.JobNotesPromptBuilder;
import com.spring.job_tracker.ai.repositories.NotesAnalysisRepository;
import com.spring.job_tracker.models.JobApplication;
import com.spring.job_tracker.models.Note;
import com.spring.job_tracker.repositories.JobApplicationRepository;
import com.spring.job_tracker.repositories.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotesAnalysisService {

    private final JobApplicationRepository jobApplicationRepository;
    private final NoteRepository noteRepository;
    private final LlmClient llmClient;
    private final AiResponseParser aiResponseParser;
    private final NotesAnalysisRepository notesAnalysisRepository;
    private final NotesAnalysisHelper notesAnalysisHelper;
    private final Scheduler jdbcScheduler = Schedulers.boundedElastic();

    public NotesAnalysisService(JobApplicationRepository jobApplicationRepository, NoteRepository noteRepository, LlmClient llmClient, AiResponseParser aiResponseParser, NotesAnalysisRepository notesAnalysisRepository, NotesAnalysisHelper notesAnalysisHelper) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.noteRepository = noteRepository;
        this.llmClient = llmClient;
        this.aiResponseParser = aiResponseParser;
        this.notesAnalysisRepository = notesAnalysisRepository;
        this.notesAnalysisHelper = notesAnalysisHelper;
    }

    @Transactional
    public Mono<NotesAnalysisResponse> analyseJobAndDocument(Long jobApplicationId) {
        return Mono.fromCallable(() -> doAnalyseJobNotes(jobApplicationId))
                .subscribeOn(jdbcScheduler);
    }


    public NotesAnalysisResponse doAnalyseJobNotes(Long jobApplicationId) {
        JobApplication job = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new RuntimeException("Job application not found"));
        List<Note> notes = noteRepository.findByJobApplicationId(jobApplicationId)
                .orElseThrow(() -> new RuntimeException("Notes not found"));
        List<String> notesText = notes.stream().map(Note::getContent).toList();
        String prompt = JobNotesPromptBuilder.build(job.getTitle(), notesText);
        String rawResponse = llmClient.generate(prompt, ESinkEventStatuses.JOB_NOTES_ANALYSIS_COMPLETE.name(), ESinkEventStatuses.JOB_AND_DOCUMENT_ANALYSIS_ERROR.name()).block();
        NotesAnalysisResponse analysis = aiResponseParser.parseNotesAnalysis(rawResponse);
        notesAnalysisRepository.findByJobApplicationId(jobApplicationId).ifPresent(notesAnalysisRepository::delete);
        NotesAnalysis entity = notesAnalysisHelper.toEntity(analysis);
        entity.setJobApplication(job);

        NotesAnalysis saved = notesAnalysisRepository.save(entity);
        return notesAnalysisHelper.toResponse(saved);
    }

    public NotesAnalysisResponse getNotesAnalysisByJobApplication(Long jobApplicationId) {
        return notesAnalysisRepository.findByJobApplicationId(jobApplicationId)
                .map(notesAnalysisHelper::toResponse)
                .orElseThrow(() -> new RuntimeException("Notes analysis not found"));
    }

    public List<NotesAnalysisResponse> getJobNotesAnalyses() {
        List<NotesAnalysisResponse> result = new ArrayList<>();

        for (NotesAnalysis analysis : notesAnalysisRepository.findAll()) {
            result.add(notesAnalysisHelper.toResponse(analysis));
        }

        return result;
    }

    public Boolean deleteNoteAnalysis(Long noteAnalysisId) {
        if (notesAnalysisRepository.findById(noteAnalysisId).isPresent()) {
            notesAnalysisRepository.deleteById(noteAnalysisId);
            return true;
        }
        return false;
    }
}
