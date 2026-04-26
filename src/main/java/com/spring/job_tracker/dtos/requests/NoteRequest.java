package com.spring.job_tracker.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {

    private Long id;
    private Long jobApplicationId;
    private String content;
}
