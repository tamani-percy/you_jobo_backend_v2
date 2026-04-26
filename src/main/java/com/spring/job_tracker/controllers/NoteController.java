package com.spring.job_tracker.controllers;

import com.spring.job_tracker.dtos.requests.NoteRequest;
import com.spring.job_tracker.dtos.responses.NoteResponse;
import com.spring.job_tracker.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("")
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteRequest noteRequest) {
        return ResponseEntity.ok(noteService.createNote(noteRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.deleteNoteById(id));
    }

    @PatchMapping("")
    public ResponseEntity<NoteResponse> updateNote(@RequestBody NoteRequest noteRequest) {
        return ResponseEntity.ok(noteService.updateNote(noteRequest));
    }

    @GetMapping("/job-application/{id}")
    public ResponseEntity<List<NoteResponse>> getNotesByJobApplication(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getAllNotesByJobApplication(id));
    }

}
