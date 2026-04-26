package com.spring.job_tracker.services;

import com.spring.job_tracker.dtos.requests.ContactPersonRequest;
import com.spring.job_tracker.dtos.responses.ContactPersonResponse;
import com.spring.job_tracker.helpers.ContactPersonHelper;
import com.spring.job_tracker.models.ContactPerson;
import com.spring.job_tracker.repositories.ContactPersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactPersonService {

    private final ContactPersonRepository contactPersonRepository;
    private final ContactPersonHelper contactPersonHelper;

    public ContactPersonService(ContactPersonRepository contactPersonRepository, ContactPersonHelper contactPersonHelper) {
        this.contactPersonRepository = contactPersonRepository;
        this.contactPersonHelper = contactPersonHelper;
    }

    public ContactPersonResponse createContactPerson(ContactPersonRequest contactPersonRequest) {
        if (contactPersonRequest == null) {
            throw new IllegalArgumentException("Contact person is required");
        }

        if (contactPersonRequest.getId() != null) {
            return contactPersonHelper.toResponse(contactPersonRepository.findById(contactPersonRequest.getId()).orElseThrow(() -> new RuntimeException("Contact person not found")));
        }
        return contactPersonHelper.toResponse(contactPersonRepository.save(contactPersonHelper.toEntity(contactPersonRequest)));
    }

    public List<ContactPersonResponse> getAllContactPersons() {
        return contactPersonRepository.findAll().stream().map(
                contactPersonHelper::toResponse
        ).toList();
    }

    public ContactPersonResponse getContactPersonById(Long id) {
        if (contactPersonRepository.findById(id).isPresent()) {
            return contactPersonHelper.toResponse(contactPersonRepository.findById(id).get());
        } else {
            throw new RuntimeException("Contact person not found");
        }
    }

    public boolean deleteContactPersonById(Long id) {
        if (contactPersonRepository.findById(id).isPresent()) {
            contactPersonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ContactPersonResponse updateContactPerson(ContactPersonRequest contactPersonRequest) {
        if (contactPersonRequest == null) {
            throw new IllegalArgumentException("Contact person is required");
        }

        ContactPerson existingContact = contactPersonRepository.findById(contactPersonRequest.getId()).orElseThrow(() -> new RuntimeException("Contact person not found"));

        contactPersonHelper.updateEntity(existingContact, contactPersonRequest);
        return contactPersonHelper.toResponse(contactPersonRepository.save(existingContact));
    }


    public List<ContactPersonResponse> getAllContactPersonsByJobApplication(Long id) {
        return contactPersonRepository.getAllByJobApplicationId(id).orElseThrow(() -> new RuntimeException("No contact persons exist by this job application.")).stream().map(contactPersonHelper::toResponse).toList();
    }
}
