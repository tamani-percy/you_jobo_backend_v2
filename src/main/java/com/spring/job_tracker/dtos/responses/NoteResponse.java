package com.spring.job_tracker.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {

    private Long id;
    private Long jobApplicationId;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
