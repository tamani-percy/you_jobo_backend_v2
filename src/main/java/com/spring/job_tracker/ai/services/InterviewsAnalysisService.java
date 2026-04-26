package com.spring.job_tracker.ai.services;

import com.spring.job_tracker.ai.client.LlmClient;
import com.spring.job_tracker.ai.dto.requests.JobPreInterviewAnalysisRequest;
import com.spring.job_tracker.ai.dto.responses.interviews.InterviewPatternAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.PostInterviewAnalysisResponse;
import com.spring.job_tracker.ai.dto.responses.interviews.PreInterviewAnalysisResponse;
import com.spring.job_tracker.ai.helpers.InterviewsHelper;
import com.spring.job_tracker.ai.models.estatuses.ESinkEventStatuses;
import com.spring.job_tracker.ai.models.interviews.InterviewPatternAnalysis;
import com.spring.job_tracker.ai.models.interviews.PostInterviewAnalysis;
import com.spring.job_tracker.ai.models.interviews.PreInterviewAnalysis;
import com.spring.job_tracker.ai.parser.AiResponseParser;
import com.spring.job_tracker.ai.prompts.interviews.InterviewPatternPromptBuilder;
import com.spring.job_tracker.ai.prompts.interviews.PostInterviewAnalysisPromptBuilder;
import com.spring.job_tracker.ai.prompts.interviews.PreInterviewPromptBuilder;
import com.spring.job_tracker.ai.repositories.interviews.InterviewPatternAnalysisRepository;
import com.spring.job_tracker.ai.repositories.interviews.PostInterviewAnalysisRepository;
import com.spring.job_tracker.ai.repositories.interviews.PreInterviewAnalysisRepository;
import com.spring.job_tracker.models.Company;
import com.spring.job_tracker.models.Document;
import com.spring.job_tracker.models.Interview;
import com.spring.job_tracker.models.JobApplication;
import com.spring.job_tracker.repositories.DocumentRepository;
import com.spring.job_tracker.repositories.InterviewRepository;
import com.spring.job_tracker.repositories.JobApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InterviewsAnalysisService {

    private final JobApplicationRepository jobApplicationRepository;
    private final InterviewRepository interviewRepository;
    private final DocumentRepository documentRepository;
    private final ResumeTextExtractorService resumeTextExtractorService;
    private final LlmClient llmClient;
    private final AiResponseParser aiResponseParser;
    private final PreInterviewAnalysisRepository preInterviewAnalysisRepository;
    private final InterviewsHelper interviewsHelper;
    private final InterviewPatternAnalysisRepository interviewPatternAnalysisRepository;
    private final PostInterviewAnalysisRepository postInterviewAnalysisRepository;
    private final Scheduler jdbcScheduler = Schedulers.boundedElastic();

    public InterviewsAnalysisService(
            JobApplicationRepository jobApplicationRepository,
            InterviewRepository interviewRepository,
            DocumentRepository documentRepository,
            ResumeTextExtractorService resumeTextExtractorService,
            LlmClient llmClient,
            AiResponseParser aiResponseParser,
            PreInterviewAnalysisRepository preInterviewAnalysisRepository,
            InterviewsHelper interviewsHelper,
            InterviewPatternAnalysisRepository interviewPatternAnalysisRepository,
            PostInterviewAnalysisRepository postInterviewAnalysisRepository
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.interviewRepository = interviewRepository;
        this.documentRepository = documentRepository;
        this.resumeTextExtractorService = resumeTextExtractorService;
        this.llmClient = llmClient;
        this.aiResponseParser = aiResponseParser;
        this.preInterviewAnalysisRepository = preInterviewAnalysisRepository;
        this.interviewsHelper = interviewsHelper;
        this.interviewPatternAnalysisRepository = interviewPatternAnalysisRepository;
        this.postInterviewAnalysisRepository = postInterviewAnalysisRepository;
    }

    @Transactional
    public Mono<PreInterviewAnalysisResponse> analysePreInterview(JobPreInterviewAnalysisRequest request) {
        return Mono.fromCallable(() -> doAnalysePreInterview(request))
                .subscribeOn(jdbcScheduler);
    }

    @Transactional
    public Mono<InterviewPatternAnalysisResponse> analyseInterviewPattern(Long jobApplicationId) {
        return Mono.fromCallable(() -> doAnalyseInterviewPattern(jobApplicationId))
                .subscribeOn(jdbcScheduler);
    }

    @Transactional
    public Mono<PostInterviewAnalysisResponse> analysePostInterview(Long jobApplicationId) {
        return Mono.fromCallable(() -> doAnalysePostInterview(jobApplicationId))
                .subscribeOn(jdbcScheduler);
    }

    private PreInterviewAnalysisResponse doAnalysePreInterview(JobPreInterviewAnalysisRequest request) {
        JobApplication job = getJobApplication(request.getJobApplicationId());
        List<Interview> interviews = getInterviews(request.getJobApplicationId());
        Interview currentInterview = resolveCurrentInterview(interviews);
        List<Interview> previousInterviews = getPreviousInterviews(interviews, currentInterview.getId());

        Company company = job.getCompany();
        Document resumeDoc = documentRepository
                .findByIdAndJobApplicationId(request.getDocumentId(), request.getJobApplicationId())
                .orElseThrow(() -> new RuntimeException("Document not found for this job application"));

        String resumeText = resumeTextExtractorService.extractText(resumeDoc);
        String prompt = PreInterviewPromptBuilder.build(
                job.getTitle(),
                company != null ? company.getName() : "",
                job.getDescription(),
                getInterviewStage(currentInterview),
                getInterviewType(currentInterview),
                resumeText,
                buildPreviousInterviewHistoryStr(previousInterviews)
        );

        String rawResponse = llmClient.generate(prompt, ESinkEventStatuses.JOB_PRE_INTERVIEW_ANALYSIS_COMPLETE.name(), ESinkEventStatuses.JOB_PRE_INTERVIEW_ANALYSIS_ERROR.name()).block();
        PreInterviewAnalysisResponse analysis = aiResponseParser.parsePreInterviewAnalysis(rawResponse);

        PreInterviewAnalysis entity = preInterviewAnalysisRepository
                .findByJobApplicationId(request.getJobApplicationId())
                .orElseGet(PreInterviewAnalysis::new);
        entity.setJobApplication(job);
        interviewsHelper.updatePreInterviewAnalysisEntity(entity, analysis);

        PreInterviewAnalysis saved = preInterviewAnalysisRepository.save(entity);
        return interviewsHelper.preInterviewAnalysisResponseToResponse(saved);
    }

    public InterviewPatternAnalysisResponse doAnalyseInterviewPattern(Long jobApplicationId) {
        JobApplication job = getJobApplication(jobApplicationId);
        List<Interview> interviews = getInterviews(jobApplicationId);

        List<String> interviewContent = interviews.stream()
                .map(Interview::getNotes)
                .filter(note -> note != null && !note.isBlank())
                .map(String::trim)
                .toList();

        if (interviewContent.isEmpty()) {
            throw new RuntimeException("No interview notes found for pattern analysis");
        }

        String prompt = InterviewPatternPromptBuilder.build(
                job.getTitle(),
                interviewContent
        );

        String rawResponse = llmClient.generate(prompt, ESinkEventStatuses.JOB_INTERVIEW_PATTERN_ANALYSIS_COMPLETE.name(), ESinkEventStatuses.JOB_INTERVIEW_PATTERN_ANALYSIS_ERROR.name()).block();
        InterviewPatternAnalysisResponse analysis = aiResponseParser.parseInterviewPatternAnalysis(rawResponse);


        InterviewPatternAnalysis entity = interviewPatternAnalysisRepository
                .findByJobApplicationId(jobApplicationId)
                .orElseGet(InterviewPatternAnalysis::new);

        entity.setJobApplication(job);
        interviewsHelper.updateInterviewPatternAnalysisEntity(entity, analysis);

        InterviewPatternAnalysis saved = interviewPatternAnalysisRepository.save(entity);
        return interviewsHelper.interviewPatternAnalysisResponseToResponse(saved);
    }

    public PostInterviewAnalysisResponse doAnalysePostInterview(Long jobApplicationId) {
        JobApplication job = getJobApplication(jobApplicationId);
        List<Interview> interviews = getInterviews(jobApplicationId);

        Interview currentInterview = interviews.stream()
                .filter(i -> i.getNotes() != null && !i.getNotes().isBlank())
                .max(Comparator.comparing(
                        Interview::getScheduledAt,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .orElseThrow(() -> new RuntimeException("No completed interview with notes found"));

        List<Interview> previousInterviews = getPreviousInterviews(interviews, currentInterview.getId());
        String previousInterviewSummary = buildPreviousInterviewSummary(previousInterviews);

        String prompt = PostInterviewAnalysisPromptBuilder.build(
                job.getTitle(),
                getInterviewStage(currentInterview),
                safe(currentInterview.getNotes()),
                previousInterviewSummary
        );

        String rawResponse = llmClient.generate(prompt, ESinkEventStatuses.JOB_POST_INTERVIEW_ANALYSIS_COMPLETE.name(), ESinkEventStatuses.JOB_POST_INTERVIEW_ANALYSIS_ERROR.name()).block();
        PostInterviewAnalysisResponse analysis = aiResponseParser.parsePostInterviewAnalysis(rawResponse);

        PostInterviewAnalysis entity = postInterviewAnalysisRepository
                .findByJobApplicationId(jobApplicationId)
                .orElseGet(PostInterviewAnalysis::new);

        entity.setJobApplication(job);
        interviewsHelper.updatePostInterviewAnalysisEntity(entity, analysis);

        PostInterviewAnalysis saved = postInterviewAnalysisRepository.save(entity);
        return interviewsHelper.postInterviewAnalysisResponseToResponse(saved);
    }

    public List<InterviewPatternAnalysisResponse> getAllInterviewPatternAnalyses() {
        List<InterviewPatternAnalysis> interviewPatternAnalyses =
                (List<InterviewPatternAnalysis>) interviewPatternAnalysisRepository.findAll();

        return interviewPatternAnalyses.stream()
                .map(interviewsHelper::interviewPatternAnalysisResponseToResponse)
                .toList();
    }

    public List<PreInterviewAnalysisResponse> getAllPreInterviewAnalyses() {
        List<PreInterviewAnalysis> interviewPrepAnalyses =
                (List<PreInterviewAnalysis>) preInterviewAnalysisRepository.findAll();

        return interviewPrepAnalyses.stream()
                .map(interviewsHelper::preInterviewAnalysisResponseToResponse)
                .toList();
    }

    public List<PostInterviewAnalysisResponse> getAllPostInterviewAnalyses() {
        List<PostInterviewAnalysis> postInterviewAnalyses =
                (List<PostInterviewAnalysis>) postInterviewAnalysisRepository.findAll();

        return postInterviewAnalyses.stream()
                .map(interviewsHelper::postInterviewAnalysisResponseToResponse)
                .toList();
    }

    public InterviewPatternAnalysisResponse getInterviewPatternAnalysisByJobApplicationId(Long jobApplicationId) {
        InterviewPatternAnalysis interviewPatternAnalysis = interviewPatternAnalysisRepository
                .findByJobApplicationId(jobApplicationId)
                .orElseThrow(() -> new RuntimeException("No interview pattern analysis found"));

        return interviewsHelper.interviewPatternAnalysisResponseToResponse(interviewPatternAnalysis);
    }

    public PostInterviewAnalysisResponse getPostInterviewAnalysisByJobApplicationId(Long jobApplicationId) {
        PostInterviewAnalysis postInterviewAnalysis = postInterviewAnalysisRepository
                .findByJobApplicationId(jobApplicationId)
                .orElseThrow(() -> new RuntimeException("No post interview analysis found"));

        return interviewsHelper.postInterviewAnalysisResponseToResponse(postInterviewAnalysis);
    }

    public PreInterviewAnalysisResponse getInterviewPrepAnalysisByJobApplicationId(Long jobApplicationId) {
        PreInterviewAnalysis preInterviewAnalysis = preInterviewAnalysisRepository
                .findByJobApplicationId(jobApplicationId)
                .orElseThrow(() -> new RuntimeException("No interview prep analysis found"));

        return interviewsHelper.preInterviewAnalysisResponseToResponse(preInterviewAnalysis);
    }

    public Boolean deletePostInterviewAnalysis(Long postInterviewAnalysisId) {
        if (postInterviewAnalysisRepository.findById(postInterviewAnalysisId).isPresent()) {
            postInterviewAnalysisRepository.deleteById(postInterviewAnalysisId);
            return true;
        }
        return false;
    }

    public Boolean deletePreInterviewAnalysis(Long preInterviewAnalysisId) {
        if (preInterviewAnalysisRepository.findById(preInterviewAnalysisId).isPresent()) {
            preInterviewAnalysisRepository.deleteById(preInterviewAnalysisId);
            return true;
        }
        return false;
    }

    public Boolean deleteInterviewPatternAnalysis(Long interviewPatternAnalysisId) {
        if (interviewPatternAnalysisRepository.findById(interviewPatternAnalysisId).isPresent()) {
            interviewPatternAnalysisRepository.deleteById(interviewPatternAnalysisId);
            return true;
        }
        return false;
    }

    private JobApplication getJobApplication(Long jobApplicationId) {
        return jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new RuntimeException("Job application not found"));
    }

    private List<Interview> getInterviews(Long jobApplicationId) {
        List<Interview> interviews = interviewRepository.getAllByJobApplicationId(jobApplicationId)
                .orElseThrow(() -> new RuntimeException("No interviews found"));

        if (interviews.isEmpty()) {
            throw new RuntimeException("No interviews found for job application");
        }

        return interviews;
    }

    private Interview resolveCurrentInterview(List<Interview> interviews) {
        LocalDateTime now = LocalDateTime.now();

        return interviews.stream()
                .filter(i -> i.getScheduledAt() != null)
                .filter(i -> !i.getScheduledAt().isBefore(now))
                .min(Comparator.comparing(Interview::getScheduledAt))
                .orElseGet(() -> interviews.stream()
                        .filter(i -> i.getScheduledAt() != null)
                        .max(Comparator.comparing(Interview::getScheduledAt))
                        .orElseThrow(() -> new RuntimeException("No interview with a scheduled date found")));
    }

    private List<Interview> getPreviousInterviews(List<Interview> interviews, Long currentInterviewId) {
        return interviews.stream()
                .filter(interview -> !Objects.equals(interview.getId(), currentInterviewId))
                .sorted(Comparator.comparing(
                        Interview::getScheduledAt,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .toList();
    }

    private List<String> buildPreviousInterviewHistoryStr(List<Interview> previousInterviews) {
        return previousInterviews.stream()
                .filter(interview -> interview.getNotes() != null && !interview.getNotes().isBlank())
                .map(interview -> String.format(
                        "Stage: %s | Type: %s | Scheduled At: %s | Notes: %s | Result: %s",
                        getInterviewStage(interview),
                        getInterviewType(interview),
                        interview.getScheduledAt() != null ? interview.getScheduledAt().toString() : "",
                        safe(interview.getNotes()),
                        getInterviewResult(interview)
                ))
                .toList();
    }

    private String buildPreviousInterviewSummary(List<Interview> previousInterviews) {
        List<Interview> previous = previousInterviews.stream()
                .filter(i -> i.getNotes() != null && !i.getNotes().isBlank())
                .sorted(Comparator.comparing(
                        Interview::getScheduledAt,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .toList();

        if (previous.isEmpty()) {
            return "No previous interviews available.";
        }

        return previous.stream()
                .map(i -> String.format(
                        "Stage: %s | Type: %s | Notes: %s",
                        getInterviewStage(i),
                        getInterviewType(i),
                        safe(i.getNotes())
                ))
                .collect(Collectors.joining("\n"));
    }

    private String getInterviewStage(Interview interview) {
        if (interview.getInterviewStage() == null) {
            return "";
        }
        return interview.getInterviewStage().getInterviewStage().name();
    }

    private String getInterviewType(Interview interview) {
        if (interview.getInterviewType() == null) {
            return "";
        }
        return interview.getInterviewType().getInterviewType().name();
    }

    private String getInterviewResult(Interview interview) {
        if (interview.getInterviewResult() == null) {
            return "";
        }
        return interview.getInterviewResult().getInterviewResult().name();
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }
}