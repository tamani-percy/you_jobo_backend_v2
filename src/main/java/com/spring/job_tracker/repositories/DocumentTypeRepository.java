package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.enums.EDocumentType;
import com.spring.job_tracker.models.statuses.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {

    Optional<DocumentType> findByDocumentType(EDocumentType applicationDocumentType);
}
