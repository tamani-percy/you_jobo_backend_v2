package com.spring.job_tracker.ai.repositories.interviews;

import com.spring.job_tracker.ai.models.interviews.PreInterviewAnalysis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreInterviewAnalysisRepository extends CrudRepository<PreInterviewAnalysis, Long> {
    Optional<PreInterviewAnalysis> findByJobApplicationId(Long jobApplicationId);

}
