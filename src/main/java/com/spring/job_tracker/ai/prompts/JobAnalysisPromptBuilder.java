package com.spring.job_tracker.ai.prompts;


public class JobAnalysisPromptBuilder {

    private JobAnalysisPromptBuilder() {}

    public static String build(
            String jobTitle,
            String companyName,
            String jobDescription,
            String resumeText,
            String notesText
    ) {
        return """
            You are an AI career assistant.

            Analyze the candidate's resume against the job posting.

            Job Title:
            %s

            Company:
            %s

            Job Description:
            %s

            Resume Text:
            %s

            Additional Notes:
            %s

            Return ONLY valid JSON in this exact structure:
            {
              "matchScore": 0,
              "strengths": ["..."],
              "gaps": ["..."],
              "suggestedKeywords": ["..."],
              "priorityRecommendation": "HIGH | MEDIUM | LOW",
              "summary": "..."
            }

            Rules:
            - matchScore must be from 0 to 100
            - strengths, gaps, suggestedKeywords must be arrays of strings
            - do not include markdown
            - do not include explanations outside JSON
            """.formatted(
                safe(jobTitle),
                safe(companyName),
                safe(jobDescription),
                safe(resumeText),
                safe(notesText)
        );
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}