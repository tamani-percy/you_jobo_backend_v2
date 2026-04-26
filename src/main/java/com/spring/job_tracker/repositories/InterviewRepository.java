package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.Interview;
import com.spring.job_tracker.models.statuses.InterviewResult;
import com.spring.job_tracker.models.statuses.InterviewStage;
import com.spring.job_tracker.models.statuses.InterviewType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    Optional<List<Interview>> getInterviewsByInterviewStage(InterviewStage interviewStage);

    Optional<List<Interview>> getInterviewsByInterviewType(InterviewType interviewType);

    Optional<List<Interview>> getInterviewsByInterviewResult(InterviewResult interviewResult);

    Optional<List<Interview>> getAllByJobApplicationId(Long jobApplicationId);

    Optional<List<Interview>> findTop3ByOrderByCreatedAtDesc();
}
