package com.spring.job_tracker.ai.prompts.interviews;

public class PostInterviewAnalysisPromptBuilder {

    private PostInterviewAnalysisPromptBuilder() {
    }

    public static String build(
            String jobTitle,
            String interviewStage,
            String notes,
            String previousInterviewSummary
    ) {
        return """
                You are an AI career coach.

                Analyze the candidate’s personal notes after an interview.

                These notes reflect the candidate’s own thoughts and experiences.
                Also consider previous interview history to detect patterns.

                Job Title:
                %s

                Interview Stage:
                %s

                Current Interview Notes:
                %s

                Previous Interview Summary:
                %s

                Return ONLY valid JSON in this exact structure:
                {
                  "strengths": ["..."],
                  "weaknesses": ["..."],
                  "missedOpportunities": ["..."],
                  "nextSteps": ["..."],
                  "confidenceAssessment": "LOW | MEDIUM | HIGH",
                  "patternInsights": ["..."],
                  "summary": "..."
                }

                Rules:
                - Interpret notes as self-reflection (NOT interviewer feedback)
                - Identify patterns between current and previous interviews
                - patternInsights must highlight repeated weaknesses or behaviors
                - nextSteps must directly address weaknesses
                - confidenceAssessment must be LOW, MEDIUM, or HIGH
                - do not include markdown
                - do not include explanations outside JSON
                - Ensure output starts with { and ends with }
                """.formatted(
                safe(jobTitle),
                safe(interviewStage),
                safe(notes),
                safe(previousInterviewSummary)
        );
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
