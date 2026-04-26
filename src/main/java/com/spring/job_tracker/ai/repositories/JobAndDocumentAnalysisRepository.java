package com.spring.job_tracker.ai.repositories;

import com.spring.job_tracker.ai.models.JobAndDocumentAnalysis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobAndDocumentAnalysisRepository extends CrudRepository<JobAndDocumentAnalysis, Long> {
    Optional<List<JobAndDocumentAnalysis>> findByJobApplicationId(Long id);
    Optional<JobAndDocumentAnalysis> findByDocumentId(Long documentId);
    void deleteByDocumentId(Long documentId);
}
