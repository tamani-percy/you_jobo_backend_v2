package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.enums.EWorkMode;
import com.spring.job_tracker.models.statuses.WorkMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkModeRepository extends JpaRepository<WorkMode, Long> {
    Optional<WorkMode> findByWorkMode(EWorkMode workMode);
}
