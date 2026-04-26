package com.spring.job_tracker.configs.intialisers;

import com.spring.job_tracker.models.enums.*;
import com.spring.job_tracker.models.statuses.*;
import com.spring.job_tracker.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitialiser {

    @Bean
    CommandLineRunner init(DocumentTypeRepository documentTypeRepository, ContactPersonRoleRepository contactPersonRoleRepository, InterviewResultRepository interviewResultRepository, InterviewStageRepository interviewStageRepository, InterviewTypeRepository interviewTypeRepository, JobStatusRepository jobStatusRepository, PriorityRepository priorityRepository, WorkModeRepository workModeRepository, JobTypeRepository jobTypeRepository, SourceRepository sourceRepository) {
        return args -> {
            for (EDocumentType applicationDocumentType : EDocumentType.values()) {
                if (documentTypeRepository.findByDocumentType(applicationDocumentType).isEmpty()) {
                    documentTypeRepository.save(new DocumentType(null, applicationDocumentType));
                }
            }
            for (EContactPersonRole contactPersonRole : EContactPersonRole.values()) {
                if (contactPersonRoleRepository.findByContactPersonRole(contactPersonRole).isEmpty()) {
                    contactPersonRoleRepository.save(new ContactPersonRole(null, contactPersonRole));
                }
            }
            for (EInterviewResult interviewResult : EInterviewResult.values()) {
                if (interviewResultRepository.findByInterviewResult(interviewResult).isEmpty()) {
                    interviewResultRepository.save(new InterviewResult(null, interviewResult));
                }
            }
            for (EInterviewStage interviewStage : EInterviewStage.values()) {
                if (interviewStageRepository.findByInterviewStage(interviewStage).isEmpty()) {
                    interviewStageRepository.save(new InterviewStage(null, interviewStage));
                }
            }
            for (EJobStatus jobStatus : EJobStatus.values()) {
                if (jobStatusRepository.findByJobStatus(jobStatus).isEmpty()) {
                    jobStatusRepository.save(new JobStatus(null, jobStatus));
                }
            }
            for (EPriority priority : EPriority.values()) {
                if (priorityRepository.findByPriority(priority).isEmpty()) {
                    priorityRepository.save(new Priority(null, priority));
                }
            }
            for (EWorkMode workMode : EWorkMode.values()) {
                if (workModeRepository.findByWorkMode(workMode).isEmpty()) {
                    workModeRepository.save(new WorkMode(null, workMode));
                }
            }
            for (EInterviewType interviewType : EInterviewType.values()) {
                if (interviewTypeRepository.findByInterviewType(interviewType).isEmpty()) {
                    interviewTypeRepository.save(new InterviewType(null, interviewType));
                }
            }
            for (EJobType jobType : EJobType.values()) {
                if (jobTypeRepository.findByJobType(jobType).isEmpty()) {
                    jobTypeRepository.save(new JobType(null, jobType));
                }
            }

            for (ESource source : ESource.values()) {
                if (sourceRepository.findBySource(source).isEmpty()) {
                    sourceRepository.save(new Source(null, source));
                }
            }
        };
    }
}
