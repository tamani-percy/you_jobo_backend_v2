package com.spring.job_tracker.helpers;

import com.spring.job_tracker.dtos.requests.CompanyRequest;
import com.spring.job_tracker.dtos.responses.CompanyResponse;
import com.spring.job_tracker.models.Company;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CompanyHelper {

    public Company toEntity(CompanyRequest companyRequest) {
        Company newCompany = new Company();
        newCompany.setName(companyRequest.getName());
        newCompany.setWebsite(companyRequest.getWebsite());
        newCompany.setIndustry(companyRequest.getIndustry());
        newCompany.setLocation(companyRequest.getLocation());
        newCompany.setDescription(companyRequest.getDescription());
        return newCompany;
    }

    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setWebsite(company.getWebsite());
        companyResponse.setIndustry(company.getIndustry());
        companyResponse.setLocation(company.getLocation());
        companyResponse.setDescription(company.getDescription());
        companyResponse.setCreatedAt(company.getCreatedAt());
        companyResponse.setUpdatedAt(company.getUpdatedAt());
        return companyResponse;
    }

    public void updateEntity(Company company, CompanyRequest companyRequest) {
        if (companyRequest.getName() != null) {
            company.setName(companyRequest.getName());
        }

        if (companyRequest.getDescription() != null) {
            company.setDescription(companyRequest.getDescription());
        }
        if (companyRequest.getWebsite() != null) {
            company.setWebsite(companyRequest.getWebsite());
        }
        if (companyRequest.getIndustry() != null) {
            company.setIndustry(companyRequest.getIndustry());
        }
        if (companyRequest.getLocation() != null) {
            company.setLocation(companyRequest.getLocation());
        }

        company.setUpdatedAt(LocalDateTime.now());

    }
}
