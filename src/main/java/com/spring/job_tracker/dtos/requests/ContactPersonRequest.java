package com.spring.job_tracker.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactPersonRequest {
    private Long id;
    private Long jobApplicationId;

    private String fullName;
    private String email;
    private String phone;
    private String linkedinUrl;
    private String notes;

    private String role;
}
