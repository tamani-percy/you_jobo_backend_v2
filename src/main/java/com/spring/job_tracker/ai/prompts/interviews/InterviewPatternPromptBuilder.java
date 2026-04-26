package com.spring.job_tracker.ai.prompts.interviews;

import java.util.List;

public class InterviewPatternPromptBuilder {

    private InterviewPatternPromptBuilder() {
    }

    public static String build(
            String jobTitle,
            List<String> allInterviewNotes
    ) {
        return """
        You are an AI career analyst.

        Analyze multiple interview notes from a candidate and identify patterns.

        Job Title:
        %s

        Interview Notes:
        %s

        Return ONLY valid JSON in this exact structure:
        {
          "recurringStrengths": ["..."],
          "recurringWeaknesses": ["..."],
          "behavioralPatterns": ["..."],
          "skillGaps": ["..."],
          "recommendedFocusAreas": ["..."],
          "overallAssessment": "..."
        }

        Rules:
        - Identify patterns across multiple interviews
        - recurringWeaknesses must highlight repeated issues
        - recommendedFocusAreas must be prioritized
        - overallAssessment should summarize candidate readiness
        - do not include markdown
        - do not include explanations outside JSON
        - Ensure output starts with { and ends with }
        """.formatted(
                safe(jobTitle),
                formatNotes(allInterviewNotes)
        );
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }

    private static String formatNotes(List<String> notes) {
        if (notes == null || notes.isEmpty()) return "";

        return notes.stream()
                .filter(n -> n != null && !n.isBlank())
                .map(n -> "- " + n.trim())
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }
}
