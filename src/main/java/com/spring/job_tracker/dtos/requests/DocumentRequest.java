package com.spring.job_tracker.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {

    private Long id;
    private Long jobApplicationId;
    private String version;

    private String documentType;
}
