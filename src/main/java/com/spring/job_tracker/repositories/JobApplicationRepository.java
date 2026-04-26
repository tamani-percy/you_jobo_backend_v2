package com.spring.job_tracker.repositories;

import com.spring.job_tracker.dtos.projections.JobStatusCount;
import com.spring.job_tracker.models.JobApplication;
import com.spring.job_tracker.models.enums.EJobStatus;
import com.spring.job_tracker.models.statuses.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Optional<List<JobApplication>> getJobApplicationsByCompanyId(Long companyId);

    Long countAllByStatus(JobStatus jobStatus);

    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.status.jobStatus IN :statuses")
    Long countByStatuses(@Param("statuses") List<EJobStatus> statuses);

    @Query("SELECT ja.status.jobStatus AS status, COUNT(ja) AS count FROM JobApplication ja GROUP BY ja.status")
    List<JobStatusCount> countGroupedByStatus();

    Optional<List<JobApplication>> findTop5ByStatus_JobStatusNotInOrderByUpdatedAtAsc(List<EJobStatus> jobStatus);


    List<JobApplication> findByStatusNotIn(List<JobStatus> excluded);
}
