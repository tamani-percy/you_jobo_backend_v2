package com.spring.job_tracker.services;

import com.spring.job_tracker.dtos.requests.InterviewRequest;
import com.spring.job_tracker.dtos.responses.InterviewResponse;
import com.spring.job_tracker.helpers.InterviewHelper;
import com.spring.job_tracker.models.Interview;
import com.spring.job_tracker.models.enums.EInterviewResult;
import com.spring.job_tracker.models.enums.EInterviewStage;
import com.spring.job_tracker.models.enums.EInterviewType;
import com.spring.job_tracker.repositories.InterviewRepository;
import com.spring.job_tracker.repositories.InterviewResultRepository;
import com.spring.job_tracker.repositories.InterviewStageRepository;
import com.spring.job_tracker.repositories.InterviewTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final InterviewHelper interviewHelper;
    private final InterviewStageRepository interviewStageRepository;
    private final InterviewTypeRepository interviewTypeRepository;
    private final InterviewResultRepository interviewResultRepository;

    public InterviewService(InterviewRepository interviewRepository, InterviewHelper interviewHelper, InterviewStageRepository interviewStageRepository, InterviewTypeRepository interviewTypeRepository, InterviewResultRepository interviewResultRepository) {
        this.interviewRepository = interviewRepository;
        this.interviewHelper = interviewHelper;
        this.interviewStageRepository = interviewStageRepository;
        this.interviewTypeRepository = interviewTypeRepository;
        this.interviewResultRepository = interviewResultRepository;
    }

    public InterviewResponse createInterview(InterviewRequest interviewRequest) {
        if (interviewRequest == null) {
            throw new IllegalArgumentException("Interview is required");
        }

        if (interviewRequest.getId() != null) {
            return interviewHelper.toResponse(interviewRepository.findById(interviewRequest.getId()).orElseThrow(() -> new RuntimeException("Interview is required")));
        }
        return interviewHelper.toResponse(interviewRepository.save(interviewHelper.toEntity(interviewRequest)));
    }

    public List<InterviewResponse> getAllInterviews() {
        return interviewRepository.findAll().stream().map(interviewHelper::toResponse).toList();
    }

    public InterviewResponse getInterviewById(Long id) {
        if (interviewRepository.findById(id).isPresent()) {
            return interviewHelper.toResponse(interviewRepository.findById(id).get());
        } else {
            throw new RuntimeException("Company not found");
        }
    }

    public boolean deleteInterviewById(Long id) {
        if (interviewRepository.findById(id).isPresent()) {
            interviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public InterviewResponse updateInterviewById(InterviewRequest interviewRequest) {
        if (interviewRequest == null) {
            throw new IllegalArgumentException("Interview is required");
        }

        Interview existingInterview = interviewRepository.findById(interviewRequest.getId()).orElseThrow(() -> new RuntimeException("Interview not found"));

        interviewHelper.updateEntity(existingInterview, interviewRequest);

        return interviewHelper.toResponse(interviewRepository.save(existingInterview));
    }

    public List<InterviewResponse> getAllInterviewsByInterviewStage(String interviewStage) {
        return interviewRepository.getInterviewsByInterviewStage(interviewStageRepository.findByInterviewStage(EInterviewStage.valueOf(interviewStage)).orElseThrow(() -> new RuntimeException("Interview stage not found"))).orElseThrow(() -> new RuntimeException("Interviews not found")).stream().map(interviewHelper::toResponse).toList();
    }

    public List<InterviewResponse> getAllInterviewsByInterviewType(String interviewType) {
        return interviewRepository.getInterviewsByInterviewType(interviewTypeRepository.findByInterviewType(EInterviewType.valueOf(interviewType)).orElseThrow(() -> new RuntimeException("Interview type not found"))).orElseThrow(() -> new RuntimeException("Interviews not found")).stream().map(interviewHelper::toResponse).toList();
    }

    public List<InterviewResponse> getAllInterviewsByInterviewResult(String interviewResult) {
        return interviewRepository.getInterviewsByInterviewResult(interviewResultRepository.findByInterviewResult(EInterviewResult.valueOf(interviewResult)).orElseThrow(() -> new RuntimeException("Interview result not found"))).orElseThrow(() -> new RuntimeException("Interviews not found")).stream().map(interviewHelper::toResponse).toList();
    }

    public List<InterviewResponse> getAllInterviewsByJobApplication(Long id) {
        return interviewRepository.getAllByJobApplicationId(id).orElseThrow(() -> new RuntimeException("No interviews found by this job application.")).stream().map(interviewHelper::toResponse).toList();
    }

    // STATISTICS
    public Long countAllInterviews() {
        return interviewRepository.count();
    }

    public List<InterviewResponse> getMostRecentInterviews() {
        return interviewRepository.findTop3ByOrderByCreatedAtDesc().orElseThrow(() -> new RuntimeException("No interviews exist")).stream().map(interviewHelper::toResponse).toList();
    }
}
