package com.spring.job_tracker.ai.helpers;

import com.spring.job_tracker.ai.dto.responses.NotesAnalysisResponse;
import com.spring.job_tracker.ai.models.NotesAnalysis;
import org.springframework.stereotype.Component;

@Component
public class NotesAnalysisHelper {

    public NotesAnalysis toEntity(NotesAnalysisResponse notesAnalysisResponse) {
        NotesAnalysis notesAnalysis = new NotesAnalysis();
        notesAnalysis.setStrengths(notesAnalysisResponse.getStrengths());
        notesAnalysis.setSummary(notesAnalysisResponse.getSummary());
        notesAnalysis.setConcerns(notesAnalysisResponse.getConcerns());
        notesAnalysis.setNextSteps(notesAnalysisResponse.getNextSteps());
        notesAnalysis.setPatterns(notesAnalysisResponse.getPatterns());
        return notesAnalysis;
    }

    public NotesAnalysisResponse toResponse(NotesAnalysis notesAnalysis) {
        NotesAnalysisResponse notesAnalysisResponse = new NotesAnalysisResponse();
        notesAnalysisResponse.setId(notesAnalysis.getId());
        notesAnalysisResponse.setCreatedAt(notesAnalysis.getCreatedAt());
        notesAnalysisResponse.setJobApplicationId(notesAnalysis.getJobApplication().getId());
        notesAnalysisResponse.setStrengths(notesAnalysis.getStrengths());
        notesAnalysisResponse.setSummary(notesAnalysis.getSummary());
        notesAnalysisResponse.setConcerns(notesAnalysis.getConcerns());
        notesAnalysisResponse.setNextSteps(notesAnalysis.getNextSteps());
        notesAnalysisResponse.setPatterns(notesAnalysis.getPatterns());
        return notesAnalysisResponse;
    }
}
