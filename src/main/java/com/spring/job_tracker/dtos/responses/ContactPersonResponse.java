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
public class ContactPersonResponse {

    private Long id;
    private Long jobApplicationId;

    private String fullName;
    private String email;
    private String phone;
    private String linkedinUrl;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String role;
}
