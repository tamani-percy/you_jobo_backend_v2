package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.enums.ESource;
import com.spring.job_tracker.models.statuses.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {

    Optional<Source> findBySource(ESource source);
}
