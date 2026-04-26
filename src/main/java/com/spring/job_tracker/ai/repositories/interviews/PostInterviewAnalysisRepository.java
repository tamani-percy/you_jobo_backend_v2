package com.spring.job_tracker.ai.repositories.interviews;

import com.spring.job_tracker.ai.models.interviews.PostInterviewAnalysis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostInterviewAnalysisRepository extends CrudRepository<PostInterviewAnalysis, Long> {
    Optional<PostInterviewAnalysis> findByJobApplicationId(Long jobApplicationId);
}
