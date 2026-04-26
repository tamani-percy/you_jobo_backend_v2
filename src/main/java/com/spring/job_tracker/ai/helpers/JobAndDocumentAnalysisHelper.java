package com.spring.job_tracker.ai.helpers;

import com.spring.job_tracker.ai.dto.responses.JobAndDocumentAnalysisResponse;
import com.spring.job_tracker.ai.models.JobAndDocumentAnalysis;
import com.spring.job_tracker.models.JobApplication;
import org.springframework.stereotype.Component;

@Component
public class JobAndDocumentAnalysisHelper {

    public JobAndDocumentAnalysis toEntity(JobAndDocumentAnalysisResponse jobAndDocumentAnalysisResponse, JobApplication jobApplication) {
        JobAndDocumentAnalysis jobAndDocumentAnalysis = new JobAndDocumentAnalysis();
        jobAndDocumentAnalysis.setJobApplication(jobApplication);
        jobAndDocumentAnalysis.setSummary(jobAndDocumentAnalysisResponse.getSummary());
        jobAndDocumentAnalysis.setMatchScore(jobAndDocumentAnalysisResponse.getMatchScore());
        jobAndDocumentAnalysis.setPriorityRecommendation(jobAndDocumentAnalysisResponse.getPriorityRecommendation());
        jobAndDocumentAnalysis.setGaps(jobAndDocumentAnalysisResponse.getGaps());
        jobAndDocumentAnalysis.setStrengths(jobAndDocumentAnalysisResponse.getStrengths());
        jobAndDocumentAnalysis.setSuggestedKeywords(jobAndDocumentAnalysisResponse.getSuggestedKeywords());
        return jobAndDocumentAnalysis;
    }

    public JobAndDocumentAnalysisResponse toResponse(JobAndDocumentAnalysis jobAndDocumentAnalysis) {
        JobAndDocumentAnalysisResponse jobAndDocumentAnalysisResponse = new JobAndDocumentAnalysisResponse();
        jobAndDocumentAnalysisResponse.setId(jobAndDocumentAnalysis.getId());
        jobAndDocumentAnalysisResponse.setSummary(jobAndDocumentAnalysis.getSummary());
        jobAndDocumentAnalysisResponse.setGaps(jobAndDocumentAnalysis.getGaps());
        jobAndDocumentAnalysisResponse.setStrengths(jobAndDocumentAnalysis.getStrengths());
        jobAndDocumentAnalysisResponse.setMatchScore(jobAndDocumentAnalysis.getMatchScore());
        jobAndDocumentAnalysisResponse.setPriorityRecommendation(jobAndDocumentAnalysis.getPriorityRecommendation());
        jobAndDocumentAnalysisResponse.setSuggestedKeywords(jobAndDocumentAnalysis.getSuggestedKeywords());
        jobAndDocumentAnalysisResponse.setCreatedAt(jobAndDocumentAnalysis.getCreatedAt());
        jobAndDocumentAnalysisResponse.setJobApplicationId(jobAndDocumentAnalysis.getJobApplication().getId());
        return jobAndDocumentAnalysisResponse;
    }
}
