package com.spring.job_tracker.ai.prompts.interviews;

import java.util.List;
import java.util.stream.Collectors;

public class PreInterviewPromptBuilder {

    private PreInterviewPromptBuilder() {
    }

    public static String build(
            String jobTitle,
            String companyName,
            String jobDescription,
            String currentInterviewStage,
            String currentInterviewType,
            String resumeText,
            List<String> previousInterviewHistory
    ) {
        return """
                You are an AI career coach.

                Generate interview preparation guidance for the candidate's CURRENT interview.
                A single job application may have multiple interviews across different stages and interview types.
                Use the current interview details as the main focus, and use previous interview history for context.

                Job Title:
                %s

                Company:
                %s

                Job Description:
                %s

                Current Interview Stage:
                %s

                Current Interview Type:
                %s

                Candidate Resume:
                %s

                Previous Interview History:
                %s

                Return ONLY valid JSON in this exact structure:
                {
                  "likelyQuestions": ["..."],
                  "technicalTopics": ["..."],
                  "preparationTips": ["..."],
                  "focusAreas": ["..."],
                  "confidenceTips": ["..."],
                  "historyBasedAdvice": ["..."]
                }

                Rules:
                - Focus primarily on the CURRENT interview stage and type
                - Use previous interview history only as supporting context
                - likelyQuestions must contain 5 to 10 realistic questions for this round
                - technicalTopics must list the main technical areas to revise
                - preparationTips must be specific and actionable
                - focusAreas must highlight candidate weaknesses or high-priority topics
                - confidenceTips must contain communication or mindset advice
                - historyBasedAdvice must mention patterns or lessons from previous interview rounds
                - If previous interview history is empty, return an empty array for historyBasedAdvice
                - Do not include markdown
                - Do not include explanations outside JSON
                - Ensure output starts with { and ends with }
                """.formatted(
                safe(jobTitle),
                safe(companyName),
                safe(jobDescription),
                safe(currentInterviewStage),
                safe(currentInterviewType),
                safe(resumeText),
                formatList(previousInterviewHistory)
        );
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }

    private static String formatList(List<String> items) {
        if (items == null || items.isEmpty()) {
            return "";
        }

        return items.stream()
                .filter(item -> item != null && !item.isBlank())
                .map(item -> "- " + item.trim())
                .collect(Collectors.joining("\n"));
    }
}