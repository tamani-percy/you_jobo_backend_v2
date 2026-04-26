package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.enums.EPriority;
import com.spring.job_tracker.models.statuses.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    Optional<Priority> findByPriority(EPriority priority);
}
