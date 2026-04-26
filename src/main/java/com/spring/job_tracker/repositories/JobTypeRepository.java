package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.enums.EJobType;
import com.spring.job_tracker.models.statuses.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobTypeRepository extends JpaRepository<JobType, Long> {

    Optional<JobType> findByJobType(EJobType jobType);
}
