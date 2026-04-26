package com.spring.job_tracker.ai.parser;

import com.spring.job_tracker.ai.dto.responses.JobAndDocumentAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.NotesAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.InterviewPatternAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.PreInterviewAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.PostInterviewAnalysisResponse;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class AiResponseParser {

    private final ObjectMapper objectMapper;

    public AiResponseParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JobAndDocumentAnalysisResponse parseJobAndDocumentAnalysis(String rawResponse) {
        try {
            return objectMapper.readValue(rawResponse, JobAndDocumentAnalysisResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + rawResponse, e);
        }
    }

    public NotesAnalysisResponse parseNotesAnalysis(String rawResponse) {
        try {
            return objectMapper.readValue(rawResponse, NotesAnalysisResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + rawResponse, e);
        }
    }

    // INTERVIEWS
    public PreInterviewAnalysisResponse parsePreInterviewAnalysis(String rawResponse) {
        try {
            return objectMapper.readValue(rawResponse, PreInterviewAnalysisResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + rawResponse, e);
        }
    }

    public InterviewPatternAnalysisResponse parseInterviewPatternAnalysis(String rawResponse) {
        try {
            return objectMapper.readValue(rawResponse, InterviewPatternAnalysisResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + rawResponse, e);
        }
    }

    public PostInterviewAnalysisResponse parsePostInterviewAnalysis(String rawResponse) {
        try {
            return objectMapper.readValue(rawResponse, PostInterviewAnalysisResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + rawResponse, e);
        }
    }
}