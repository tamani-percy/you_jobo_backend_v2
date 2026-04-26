package com.spring.job_tracker.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    private Long id;
    private String name;
    private String website;
    private String industry;
    private String location;
    private String description;
}
