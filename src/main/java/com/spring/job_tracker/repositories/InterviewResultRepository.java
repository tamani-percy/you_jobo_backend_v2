package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.enums.EInterviewResult;
import com.spring.job_tracker.models.statuses.InterviewResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewResultRepository extends JpaRepository<InterviewResult, Long> {

    Optional<InterviewResult> findByInterviewResult(EInterviewResult interviewResult);
}
