package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.enums.EInterviewType;
import com.spring.job_tracker.models.statuses.InterviewType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewTypeRepository extends JpaRepository<InterviewType, Long> {
    Optional<InterviewType> findByInterviewType(EInterviewType interviewType);
}
