package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.enums.EInterviewStage;
import com.spring.job_tracker.models.statuses.InterviewStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewStageRepository extends JpaRepository<InterviewStage, Long> {
    Optional<InterviewStage> findByInterviewStage(EInterviewStage interviewStage);
}
