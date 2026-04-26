package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<List<Note>> getAllByJobApplicationId(Long jobApplicationId);

    Optional<List<Note>> findByJobApplicationId(Long jobApplicationId);
}
