package com.spring.job_tracker.services;

import com.spring.job_tracker.dtos.requests.NoteRequest;
import com.spring.job_tracker.dtos.responses.NoteResponse;
import com.spring.job_tracker.helpers.NoteHelper;
import com.spring.job_tracker.models.Note;
import com.spring.job_tracker.repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteHelper noteHelper;

    public NoteService(NoteRepository noteRepository, NoteHelper noteHelper) {
        this.noteRepository = noteRepository;
        this.noteHelper = noteHelper;
    }

    public NoteResponse createNote(NoteRequest noteRequest) {
        if (noteRequest == null) {
            throw new IllegalArgumentException("Note is required");
        }

        if (noteRequest.getId() != null) {
            return noteHelper.toResponse(noteRepository.findById(noteRequest.getId()).orElseThrow(() -> new RuntimeException("Note is required")));
        }
        return noteHelper.toResponse(noteRepository.save(noteHelper.toEntity(noteRequest)));
    }

    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAll().stream().map(noteHelper::toResponse).toList();
    }

    public NoteResponse getNoteById(Long id) {
        if (noteRepository.findById(id).isPresent()) {
            return noteHelper.toResponse(noteRepository.findById(id).get());
        } else {
            throw new RuntimeException("Note not found");
        }
    }

    public boolean deleteNoteById(Long id) {
        if (noteRepository.findById(id).isPresent()) {
            noteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public NoteResponse updateNote(NoteRequest noteRequest) {
        if (noteRequest == null) {
            throw new IllegalArgumentException("Note is required");
        }

        Note existingNote = noteRepository.findById(noteRequest.getId()).orElseThrow(() -> new RuntimeException("Note not found"));

        noteHelper.updateEntity(existingNote, noteRequest);
        return noteHelper.toResponse(noteRepository.save(existingNote));
    }

    public List<NoteResponse> getAllNotesByJobApplication(Long id) {
        return noteRepository.getAllByJobApplicationId(id).orElseThrow(() -> new RuntimeException("No notes found by this job application.")).stream().map(noteHelper::toResponse).toList();
    }
}
