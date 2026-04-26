package com.spring.job_tracker.helpers;

import com.spring.job_tracker.dtos.requests.DocumentRequest;
import com.spring.job_tracker.dtos.responses.DocumentResponse;
import com.spring.job_tracker.models.Document;
import com.spring.job_tracker.models.enums.EDocumentType;
import com.spring.job_tracker.repositories.DocumentTypeRepository;
import com.spring.job_tracker.repositories.JobApplicationRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class DocumentHelper {

    private final DocumentTypeRepository documentTypeRepository;
    private final FileHelper fileHelper;
    private final JobApplicationRepository jobApplicationRepository;

    public DocumentHelper(DocumentTypeRepository documentTypeRepository, FileHelper fileHelper, JobApplicationRepository jobApplicationRepository) {
        this.documentTypeRepository = documentTypeRepository;
        this.fileHelper = fileHelper;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public Document toEntity(DocumentRequest request, MultipartFile documentFile) throws IOException {
        Document doc = new Document();
        if (request.getId() != null) {
            doc.setId(request.getId());
        }
        doc.setJobApplication(jobApplicationRepository.findById(request.getJobApplicationId()).orElseThrow(() -> new RuntimeException("Job Application Not Found")));
        doc.setDocumentType(documentTypeRepository.findByDocumentType(EDocumentType.valueOf(request.getDocumentType())).orElseThrow(null));
        String fileName = fileHelper.uploadFile(documentFile);
        doc.setFileUrl("/uploads/documents/" + fileName);
        doc.setFileKey(documentFile.getOriginalFilename());
        doc.setFileName(fileName);
        doc.setVersion(request.getVersion());
        return doc;
    }

    public DocumentResponse toResponse(Document document) {
        DocumentResponse response = new DocumentResponse();
        response.setId(document.getId());
        response.setDocumentType(document.getDocumentType().getDocumentType().name());
        response.setJobApplicationId(document.getJobApplication().getId());
        response.setFileName(document.getFileName());
        response.setFileKey(document.getFileKey());
        response.setFileUrl(document.getFileUrl());
        response.setVersion(document.getVersion());
        response.setCreatedAt(document.getCreatedAt());
        return response;
    }

    public void updateEntity(DocumentRequest request, Document document, MultipartFile file) throws IOException {

        if (request.getDocumentType() != null) {
            document.setDocumentType(
                    documentTypeRepository
                            .findByDocumentType(EDocumentType.valueOf(request.getDocumentType()))
                            .orElseThrow(() -> new RuntimeException("Document type not found"))
            );
        }

        document.setJobApplication(jobApplicationRepository.findById(request.getJobApplicationId()).orElseThrow(() -> new RuntimeException("Job Application Not Found")));


        if (file != null && !file.isEmpty()) {

            if (document.getFileUrl() != null) {
                fileHelper.deleteFile(document.getFileUrl());
            }

            String newFileName = fileHelper.uploadFile(file);

            document.setFileUrl("/uploads/documents/" + newFileName);
            document.setFileKey(file.getOriginalFilename());
            document.setFileName(newFileName);
        }

        if (request.getVersion() != null) {
            document.setVersion(request.getVersion());
        }
    }
}
