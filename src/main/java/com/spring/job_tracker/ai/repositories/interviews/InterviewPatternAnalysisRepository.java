package com.spring.job_tracker.ai.repositories.interviews;

import com.spring.job_tracker.ai.models.interviews.InterviewPatternAnalysis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewPatternAnalysisRepository extends CrudRepository<InterviewPatternAnalysis, Long> {

    Optional<InterviewPatternAnalysis> findByJobApplicationId(Long jobApplicationId);

}
