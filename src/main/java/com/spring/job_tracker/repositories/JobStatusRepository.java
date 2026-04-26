package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.enums.EJobStatus;
import com.spring.job_tracker.models.statuses.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {
    Optional<JobStatus> findByJobStatus(EJobStatus jobStatus);
}
