package com.spring.job_tracker.controllers;

import com.spring.job_tracker.dtos.requests.CompanyRequest;
import com.spring.job_tracker.dtos.responses.CompanyResponse;
import com.spring.job_tracker.dtos.responses.JobApplicationResponse;
import com.spring.job_tracker.services.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("")
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.createCompany(companyRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CompanyResponse>> getAllCompany() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @PatchMapping("")
    public ResponseEntity<CompanyResponse> updateCompany(@RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.updateCompany(companyRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.deleteCompanyById(id));
    }

    @GetMapping("/{id}/job-applications")
    public ResponseEntity<List<JobApplicationResponse>> getCompanyJobApplications(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getAllJobApplicationsByCompanyId(id));
    }

    @GetMapping("/job-application/{id}")
    public ResponseEntity<CompanyResponse> getCompanyByJobApplication(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyByJobApplicationId(id));
    }
}
