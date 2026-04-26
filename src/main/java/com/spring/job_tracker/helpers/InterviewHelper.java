package com.spring.job_tracker.helpers;


import com.spring.job_tracker.dtos.requests.InterviewRequest;
import com.spring.job_tracker.dtos.responses.InterviewResponse;
import com.spring.job_tracker.models.Interview;
import com.spring.job_tracker.models.enums.EInterviewResult;
import com.spring.job_tracker.models.enums.EInterviewStage;
import com.spring.job_tracker.models.enums.EInterviewType;
import com.spring.job_tracker.repositories.InterviewResultRepository;
import com.spring.job_tracker.repositories.InterviewStageRepository;
import com.spring.job_tracker.repositories.InterviewTypeRepository;
import com.spring.job_tracker.repositories.JobApplicationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InterviewHelper {

    private final InterviewStageRepository interviewStageRepository;
    private final InterviewTypeRepository interviewTypeRepository;
    private final InterviewResultRepository interviewResultRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public InterviewHelper(InterviewStageRepository interviewStageRepository, InterviewTypeRepository interviewTypeRepository, InterviewResultRepository interviewResultRepository, JobApplicationRepository jobApplicationRepository) {
        this.interviewStageRepository = interviewStageRepository;
        this.interviewTypeRepository = interviewTypeRepository;
        this.interviewResultRepository = interviewResultRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public Interview toEntity(InterviewRequest request) {
        Interview interview = new Interview();
        if (request.getId() != null) {
            interview.setId(request.getId());

        }
        interview.setInterviewStage(interviewStageRepository.findByInterviewStage(EInterviewStage.valueOf(request.getInterviewStage())).orElseThrow(null));
        interview.setInterviewType(interviewTypeRepository.findByInterviewType(EInterviewType.valueOf(request.getInterviewType())).orElseThrow(null));
        interview.setInterviewResult(interviewResultRepository.findByInterviewResult(EInterviewResult.valueOf(request.getInterviewResult())).orElseThrow(null));
        interview.setJobApplication(jobApplicationRepository.findById(request.getJobApplicationId()).orElseThrow(null));
        interview.setScheduledAt(request.getScheduledAt());
        interview.setDurationMinutes(request.getDurationMinutes());
        interview.setLocationOrLink(request.getLocationOrLink());
        interview.setInterviewerName(request.getInterviewerName());
        if (request.getInterviewerEmail() != null) {
            interview.setInterviewerEmail(request.getInterviewerEmail());
        }
        interview.setNotes(request.getNotes());
        return interview;
    }

    public InterviewResponse toResponse(Interview interview) {
        InterviewResponse interviewResponse = new InterviewResponse();
        interviewResponse.setId(interview.getId());
        interviewResponse.setInterviewStage(interview.getInterviewStage().getInterviewStage().name());
        interviewResponse.setInterviewType(interview.getInterviewType().getInterviewType().name());
        interviewResponse.setInterviewResult(interview.getInterviewResult().getInterviewResult().name());
        interviewResponse.setJobApplicationId(interview.getJobApplication().getId());
        interviewResponse.setScheduledAt(interview.getScheduledAt());
        interviewResponse.setDurationMinutes(interview.getDurationMinutes());
        interviewResponse.setLocationOrLink(interview.getLocationOrLink());
        interviewResponse.setInterviewerName(interview.getInterviewerName());
        interviewResponse.setCreatedAt(interview.getCreatedAt());
        if (interview.getUpdatedAt() != null) {
            interviewResponse.setUpdatedAt(interview.getUpdatedAt());
        }
        if (interview.getInterviewerEmail() != null) {
            interviewResponse.setInterviewerEmail(interview.getInterviewerEmail());
        }
        interviewResponse.setNotes(interview.getNotes());
        return interviewResponse;
    }

    public void updateEntity(Interview interview, InterviewRequest interviewRequest) {

        if (interviewRequest.getInterviewStage() != null) {
            interview.setInterviewStage(interviewStageRepository.findByInterviewStage(EInterviewStage.valueOf(interviewRequest.getInterviewStage())).orElseThrow(null));

        }

        if (interviewRequest.getInterviewType() != null) {
            interview.setInterviewType(interviewTypeRepository.findByInterviewType(EInterviewType.valueOf(interviewRequest.getInterviewType())).orElseThrow(null));

        }

        if (interviewRequest.getInterviewResult() != null) {
            interview.setInterviewResult(interviewResultRepository.findByInterviewResult(EInterviewResult.valueOf(interviewRequest.getInterviewResult())).orElseThrow(null));

        }

        if (interviewRequest.getScheduledAt() != null) {
            interview.setScheduledAt(interviewRequest.getScheduledAt());
        }

        if (interviewRequest.getInterviewerEmail() != null) {
            interview.setInterviewerEmail(interview.getInterviewerEmail());
        }

        if (interviewRequest.getInterviewerName() != null) {
            interview.setInterviewerName(interview.getInterviewerName());
        }

        if (interviewRequest.getNotes() != null) {
            interview.setNotes(interviewRequest.getNotes());
        }

        if (interviewRequest.getLocationOrLink() != null) {
            interview.setLocationOrLink(interviewRequest.getLocationOrLink());
        }

        if (interviewRequest.getDurationMinutes() != null) {
            interview.setDurationMinutes(interviewRequest.getDurationMinutes());
        }
        interview.setUpdatedAt(LocalDateTime.now());

    }
}
