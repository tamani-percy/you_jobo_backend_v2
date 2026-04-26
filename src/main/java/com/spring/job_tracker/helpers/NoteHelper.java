package com.spring.job_tracker.helpers;

import com.spring.job_tracker.dtos.requests.NoteRequest;
import com.spring.job_tracker.dtos.responses.NoteResponse;
import com.spring.job_tracker.models.Note;
import com.spring.job_tracker.repositories.JobApplicationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NoteHelper {

    private final JobApplicationRepository jobApplicationRepository;

    public NoteHelper(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public Note toEntity(NoteRequest noteRequest) {
        Note note = new Note();
        if (noteRequest.getId() != null) {
            note.setId(noteRequest.getId());
        }
        note.setContent(noteRequest.getContent());
        note.setJobApplication(jobApplicationRepository.findById(noteRequest.getJobApplicationId()).orElseThrow(null));
        return note;
    }

    public NoteResponse toResponse(Note note) {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(note.getId());
        noteResponse.setContent(note.getContent());
        noteResponse.setCreatedAt(note.getCreatedAt());
        if(note.getUpdatedAt() != null) {
            noteResponse.setUpdatedAt(note.getUpdatedAt());
        }

        noteResponse.setJobApplicationId(note.getJobApplication().getId());

        return noteResponse;
    }

    public void updateEntity(Note note, NoteRequest noteRequest) {
        if (noteRequest.getContent() != null) {
            note.setContent(noteRequest.getContent());
        }

        if (noteRequest.getJobApplicationId() != null) {
            note.setJobApplication(jobApplicationRepository.findById(noteRequest.getJobApplicationId()).orElseThrow(() -> new RuntimeException("Job application not found.")));
        }

        note.setUpdatedAt(LocalDateTime.now());
    }
}
