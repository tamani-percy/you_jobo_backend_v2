package com.spring.job_tracker.helpers;


import com.spring.job_tracker.dtos.requests.ContactPersonRequest;
import com.spring.job_tracker.dtos.responses.ContactPersonResponse;
import com.spring.job_tracker.models.ContactPerson;
import com.spring.job_tracker.models.enums.EContactPersonRole;
import com.spring.job_tracker.repositories.ContactPersonRoleRepository;
import com.spring.job_tracker.repositories.JobApplicationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ContactPersonHelper {

    private final ContactPersonRoleRepository contactPersonRoleRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public ContactPersonHelper(ContactPersonRoleRepository contactPersonRoleRepository, JobApplicationRepository jobApplicationRepository) {
        this.contactPersonRoleRepository = contactPersonRoleRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public ContactPerson toEntity(ContactPersonRequest contactPersonRequest) {
        ContactPerson contactPerson = new ContactPerson();
        if (contactPersonRequest.getId() != null) {
            contactPerson.setId(contactPersonRequest.getId());
        }
        contactPerson.setFullName(contactPersonRequest.getFullName());
        contactPerson.setContactPersonRole(contactPersonRoleRepository.findByContactPersonRole(EContactPersonRole.valueOf(contactPersonRequest.getRole())).orElseThrow(null));
        if (contactPersonRequest.getEmail() != null) {
            contactPerson.setEmail(contactPersonRequest.getEmail());

        }
        if (contactPersonRequest.getPhone() != null) {
            contactPerson.setPhone(contactPersonRequest.getPhone());
        }
        if (contactPersonRequest.getLinkedinUrl() != null) {
            contactPerson.setLinkedinUrl(contactPersonRequest.getLinkedinUrl());
        }
        if (contactPersonRequest.getNotes() != null) {
            contactPerson.setNotes(contactPersonRequest.getNotes());
        }
        contactPerson.setJobApplication(jobApplicationRepository.findById(contactPersonRequest.getJobApplicationId()).orElseThrow(null));
        return contactPerson;
    }

    public ContactPersonResponse toResponse(ContactPerson contactPerson) {
        ContactPersonResponse contactPersonResponse = new ContactPersonResponse();
        contactPersonResponse.setId(contactPerson.getId());
        contactPersonResponse.setCreatedAt(contactPerson.getCreatedAt());
        contactPersonResponse.setUpdatedAt(contactPerson.getUpdatedAt());
        contactPersonResponse.setId(contactPersonResponse.getId());
        contactPersonResponse.setFullName(contactPerson.getFullName());
        contactPersonResponse.setRole(contactPerson.getContactPersonRole().getContactPersonRole().name());
        if (contactPerson.getEmail() != null) {
            contactPersonResponse.setEmail(contactPerson.getEmail());
        }

        if (contactPerson.getJobApplication() != null) {
            contactPersonResponse.setJobApplicationId(contactPerson.getJobApplication().getId());
        }

        if (contactPerson.getPhone() != null) {
            contactPersonResponse.setPhone(contactPerson.getPhone());
        }
        if (contactPerson.getLinkedinUrl() != null) {
            contactPersonResponse.setLinkedinUrl(contactPerson.getLinkedinUrl());
        }
        if (contactPerson.getNotes() != null) {
            contactPersonResponse.setNotes(contactPerson.getNotes());
        }
        return contactPersonResponse;
    }

    public void updateEntity(ContactPerson contactPerson, ContactPersonRequest contactPersonRequest) {
        if (contactPersonRequest.getFullName() != null) {
            contactPerson.setFullName(contactPersonRequest.getFullName());
        }

        if (contactPersonRequest.getRole() != null) {
            contactPerson.setContactPersonRole(contactPersonRoleRepository.findByContactPersonRole(EContactPersonRole.valueOf(contactPersonRequest.getRole())).orElseThrow(() -> new RuntimeException("Role not found")));
        }
        if (contactPersonRequest.getEmail() != null) {
            contactPerson.setEmail(contactPersonRequest.getEmail());
        }
        if (contactPersonRequest.getPhone() != null) {
            contactPerson.setPhone(contactPersonRequest.getPhone());
        }
        if (contactPersonRequest.getLinkedinUrl() != null) {
            contactPerson.setLinkedinUrl(contactPersonRequest.getLinkedinUrl());
        }
        if (contactPersonRequest.getNotes() != null) {
            contactPerson.setNotes(contactPersonRequest.getNotes());
        }
        contactPerson.setUpdatedAt(LocalDateTime.now());

    }
}
