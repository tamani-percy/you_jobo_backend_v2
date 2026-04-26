package com.spring.job_tracker.ai.helpers;

import com.spring.job_tracker.ai.dto.responses.interviews.InterviewPatternAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.PreInterviewAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.PostInterviewAnalysisResponse;
import com.spring.job_tracker.ai.models.interviews.InterviewPatternAnalysis;
import com.spring.job_tracker.ai.models.interviews.PreInterviewAnalysis;
import com.spring.job_tracker.ai.models.interviews.PostInterviewAnalysis;
import org.springframework.stereotype.Component;

@Component
public class InterviewsHelper {

    public void updateInterviewPatternAnalysisEntity(
            InterviewPatternAnalysis entity,
            InterviewPatternAnalysisResponse response
    ) {
        entity.setBehavioralPatterns(response.getBehavioralPatterns());
        entity.setOverallAssessment(response.getOverallAssessment());
        entity.setRecommendedFocusAreas(response.getRecommendedFocusAreas());
        entity.setRecurringStrengths(response.getRecurringStrengths());
        entity.setRecurringWeaknesses(response.getRecurringWeaknesses());
        entity.setSkillGaps(response.getSkillGaps());
    }

    public InterviewPatternAnalysisResponse interviewPatternAnalysisResponseToResponse(
            InterviewPatternAnalysis entity
    ) {
        InterviewPatternAnalysisResponse response = new InterviewPatternAnalysisResponse();
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt());
        response.setBehavioralPatterns(entity.getBehavioralPatterns());
        response.setOverallAssessment(entity.getOverallAssessment());
        response.setRecommendedFocusAreas(entity.getRecommendedFocusAreas());
        response.setRecurringStrengths(entity.getRecurringStrengths());
        response.setRecurringWeaknesses(entity.getRecurringWeaknesses());
        response.setSkillGaps(entity.getSkillGaps());

        if (entity.getJobApplication() != null) {
            response.setJobApplicationId(entity.getJobApplication().getId());
        }

        return response;
    }

    public void updatePreInterviewAnalysisEntity(
            PreInterviewAnalysis entity,
            PreInterviewAnalysisResponse response
    ) {
        entity.setConfidenceTips(response.getConfidenceTips());
        entity.setFocusAreas(response.getFocusAreas());
        entity.setLikelyQuestions(response.getLikelyQuestions());
        entity.setPreparationTips(response.getPreparationTips());
        entity.setTechnicalTopics(response.getTechnicalTopics());
    }

    public PreInterviewAnalysisResponse preInterviewAnalysisResponseToResponse(
            PreInterviewAnalysis entity
    ) {
        PreInterviewAnalysisResponse response = new PreInterviewAnalysisResponse();
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt());
        response.setConfidenceTips(entity.getConfidenceTips());
        response.setFocusAreas(entity.getFocusAreas());
        response.setLikelyQuestions(entity.getLikelyQuestions());
        response.setPreparationTips(entity.getPreparationTips());
        response.setTechnicalTopics(entity.getTechnicalTopics());

        if (entity.getJobApplication() != null) {
            response.setJobApplicationId(entity.getJobApplication().getId());
        }

        return response;
    }

    public void updatePostInterviewAnalysisEntity(
            PostInterviewAnalysis entity,
            PostInterviewAnalysisResponse response
    ) {
        entity.setConfidenceAssessment(response.getConfidenceAssessment());
        entity.setSummary(response.getSummary());
        entity.setStrengths(response.getStrengths());
        entity.setMissedOpportunities(response.getMissedOpportunities());
        entity.setWeaknesses(response.getWeaknesses());
        entity.setNextSteps(response.getNextSteps());
    }

    public PostInterviewAnalysisResponse postInterviewAnalysisResponseToResponse(
            PostInterviewAnalysis entity
    ) {
        PostInterviewAnalysisResponse response = new PostInterviewAnalysisResponse();
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt());
        response.setConfidenceAssessment(entity.getConfidenceAssessment());
        response.setSummary(entity.getSummary());
        response.setStrengths(entity.getStrengths());
        response.setWeaknesses(entity.getWeaknesses());
        response.setMissedOpportunities(entity.getMissedOpportunities());
        response.setNextSteps(entity.getNextSteps());

        if (entity.getJobApplication() != null) {
            response.setJobApplicationId(entity.getJobApplication().getId());
        }

        return response;
    }
}