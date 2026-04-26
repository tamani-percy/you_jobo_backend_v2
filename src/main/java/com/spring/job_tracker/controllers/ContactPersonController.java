package com.spring.job_tracker.controllers;

import com.spring.job_tracker.dtos.requests.ContactPersonRequest;
import com.spring.job_tracker.dtos.responses.ContactPersonResponse;
import com.spring.job_tracker.services.ContactPersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact-persons")
public class ContactPersonController {

    private final ContactPersonService contactPersonService;

    public ContactPersonController(ContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @PostMapping("")
    public ResponseEntity<ContactPersonResponse> createContactPerson(@RequestBody ContactPersonRequest contactPersonRequest) {
        return ResponseEntity.ok(contactPersonService.createContactPerson(contactPersonRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactPersonResponse> getContactPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(contactPersonService.getContactPersonById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactPersonResponse>> getAllContactPersons() {
        return ResponseEntity.ok(contactPersonService.getAllContactPersons());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteContactPerson(@PathVariable Long id) {
        return ResponseEntity.ok(contactPersonService.deleteContactPersonById(id));
    }

    @PatchMapping("")
    public ResponseEntity<ContactPersonResponse> updateContactPerson(@RequestBody ContactPersonRequest contactPersonRequest) {
        return ResponseEntity.ok(contactPersonService.updateContactPerson(contactPersonRequest));
    }

    @GetMapping("/job-application/{id}")
    public ResponseEntity<List<ContactPersonResponse>> getContactPersonsByJobApplication(@PathVariable Long id) {
        return ResponseEntity.ok(contactPersonService.getAllContactPersonsByJobApplication(id));
    }
}
