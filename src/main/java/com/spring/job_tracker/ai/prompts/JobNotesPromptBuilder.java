package com.spring.job_tracker.ai.prompts;

import java.util.List;

public class JobNotesPromptBuilder {

    private JobNotesPromptBuilder() {
    }

    public static String build(
            String jobTitle,
            List<String> notes
    ) {
        return """
                    Analyze the candidate's personal notes about their job application experience.
                    These notes reflect the candidate's own thoughts after applying or interviewing.
                    Focus on self-reflection and improvement.

                Job Title:
                %s

                Notes:
                %s

                Return ONLY valid JSON in this exact structure:
                {
                  "strengths": ["..."],
                  "concerns": ["..."],
                  "nextSteps": ["..."],
                  "patterns": ["..."],
                  "summary": "..."
                }

                Rules:
                - strengths, concerns, nextSteps, patterns must be arrays of strings
                - each array should contain 2–5 concise items
                - nextSteps should be actionable (e.g., "Follow up with recruiter", "Prepare system design answers")
                - summary should be 1–2 sentences
                - do not include markdown
                - do not include explanations outside JSON
                """.formatted(
                safe(jobTitle),
                formatNotes(notes)
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
