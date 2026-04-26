package com.spring.job_tracker.services;

import com.spring.job_tracker.dtos.requests.CompanyRequest;
import com.spring.job_tracker.dtos.responses.CompanyResponse;
import com.spring.job_tracker.dtos.responses.JobApplicationResponse;
import com.spring.job_tracker.helpers.CompanyHelper;
import com.spring.job_tracker.helpers.JobApplicationHelper;
import com.spring.job_tracker.models.Company;
import com.spring.job_tracker.models.JobApplication;
import com.spring.job_tracker.repositories.CompanyRepository;
import com.spring.job_tracker.repositories.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyHelper companyHelper;
    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationHelper jobApplicationHelper;

    public CompanyService(CompanyRepository companyRepository, CompanyHelper companyHelper, JobApplicationRepository jobApplicationRepository, JobApplicationHelper jobApplicationHelper) {
        this.companyRepository = companyRepository;
        this.companyHelper = companyHelper;
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobApplicationHelper = jobApplicationHelper;
    }

    public CompanyResponse createCompany(CompanyRequest companyRequest) {
        if (companyRequest == null) {
            throw new IllegalArgumentException("Company is required");
        }

        if (companyRequest.getId() != null) {
            return companyHelper.toResponse(companyRepository.findById(companyRequest.getId()).orElseThrow(() -> new RuntimeException("Company not found")));
        }
        return companyHelper.toResponse(companyRepository.save(companyHelper.toEntity(companyRequest)));
    }

    public List<CompanyResponse> getAllCompanies() {
        return companyRepository.findAll().stream().map(companyHelper::toResponse).toList();
    }

    public CompanyResponse getCompanyById(Long id) {
        if (companyRepository.findById(id).isPresent()) {
            return companyHelper.toResponse(companyRepository.findById(id).get());
        } else {
            throw new RuntimeException("Company not found");
        }
    }

    public boolean deleteCompanyById(Long id) {
        if (companyRepository.findById(id).isPresent()) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public CompanyResponse updateCompany(CompanyRequest companyRequest) {
        if (companyRequest == null) {
            throw new IllegalArgumentException("Company is required");
        }

        Company existingCompany = companyRepository.findById(companyRequest.getId()).orElseThrow(() -> new RuntimeException("Company not found"));

        companyHelper.updateEntity(existingCompany, companyRequest);

        return companyHelper.toResponse(companyRepository.save(existingCompany));
    }

    public List<JobApplicationResponse> getAllJobApplicationsByCompanyId(Long id) {
        if (companyRepository.findById(id).isPresent()) {
            if (jobApplicationRepository.getJobApplicationsByCompanyId(id).isPresent()) {
                return jobApplicationRepository.getJobApplicationsByCompanyId(id).get().stream().map(jobApplicationHelper::toResponse).toList();
            }
        }
        throw new RuntimeException("Company not found");
    }

    public CompanyResponse getCompanyByJobApplicationId(Long id) {
        JobApplication jobApplication = jobApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("JobApplication not found"));
        return companyHelper.toResponse(companyRepository.findById(jobApplication.getCompany().getId()).orElseThrow(() -> new RuntimeException("Company does not exist")));
    }
}
