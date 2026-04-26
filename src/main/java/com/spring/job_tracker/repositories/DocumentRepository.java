package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<List<Document>> getAllByJobApplicationId(Long jobApplicationId);

    Optional<Document> findByFileKey(String fileKey);
    Optional<Document> findByFileName(String fileName);

    Optional<Document> findByIdAndJobApplicationId(Long id, Long jobApplicationId);
}
