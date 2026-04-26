package com.spring.job_tracker.ai.repositories;

import com.spring.job_tracker.ai.models.NotesAnalysis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NotesAnalysisRepository extends CrudRepository<NotesAnalysis, Long> {
    Optional<NotesAnalysis> findByJobApplicationId(Long jobApplicationId);
}
