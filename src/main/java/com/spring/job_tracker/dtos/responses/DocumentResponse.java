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
public class DocumentResponse {

    private Long id;
    private Long jobApplicationId;

    private String fileName;
    private String filePath;
    private String fileKey;
    private String fileUrl;
    private String version;

    private LocalDateTime createdAt;

    private String documentType;
}
