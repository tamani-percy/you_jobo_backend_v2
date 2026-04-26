package com.spring.job_tracker.services;

import com.spring.job_tracker.dtos.requests.DocumentRequest;
import com.spring.job_tracker.dtos.responses.DocumentResponse;
import com.spring.job_tracker.helpers.DocumentHelper;
import com.spring.job_tracker.helpers.FileHelper;
import com.spring.job_tracker.models.Document;
import com.spring.job_tracker.models.JobApplication;
import com.spring.job_tracker.repositories.DocumentRepository;
import com.spring.job_tracker.repositories.JobApplicationRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentHelper documentHelper;
    private final FileHelper fileHelper;
    private final JobApplicationRepository jobApplicationRepository;

    public DocumentService(DocumentRepository documentRepository, DocumentHelper documentHelper, FileHelper fileHelper, JobApplicationRepository jobApplicationRepository) {
        this.documentRepository = documentRepository;
        this.documentHelper = documentHelper;
        this.fileHelper = fileHelper;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public DocumentResponse createDocument(DocumentRequest documentRequest, MultipartFile file) throws IOException {
        if (jobApplicationRepository.findById(documentRequest.getJobApplicationId()).isPresent()) {
            JobApplication jobApplication = jobApplicationRepository.findById(documentRequest.getJobApplicationId()).get();
            int maxDocuments = 5;
            if (jobApplication.getDocuments().size() >= maxDocuments) {
                throw new RuntimeException("Maximum documents exceeded. Max number of documents is " + maxDocuments);
            }
            if (documentRequest.getId() != null) {
                return documentHelper.toResponse(documentRepository.findById(documentRequest.getId()).orElseThrow(() -> new RuntimeException("Document not found")));
            }
            return documentHelper.toResponse(documentRepository.save(documentHelper.toEntity(documentRequest, file)));
        }
        return null;
    }

    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll().stream().map(documentHelper::toResponse).toList();
    }

    public DocumentResponse getDocument(Long id) {
        if (documentRepository.findById(id).isPresent()) {
            return documentHelper.toResponse(documentRepository.findById(id).get());
        } else {
            throw new RuntimeException("Document not found");
        }
    }

    public boolean deleteDocument(Long id) {
        if (documentRepository.findById(id).isPresent()) {
            Document existingDocument = documentRepository.findById(id).get();
            if (existingDocument.getFileUrl() != null) {
                fileHelper.deleteFile(existingDocument.getFileUrl());
                documentRepository.deleteById(id);
                return true;
            }
            return false;

        }
        return false;
    }

    public DocumentResponse updateDocument(DocumentRequest documentRequest, MultipartFile file) throws IOException {
        if (documentRequest == null) {
            throw new IllegalArgumentException("Document is required");
        }
        Document existingDocument = documentRepository.findById(documentRequest.getId()).orElseThrow(() -> new RuntimeException("Document not found"));

        documentHelper.updateEntity(documentRequest, existingDocument, file);
        return documentHelper.toResponse(documentRepository.save(existingDocument));
    }

    public List<DocumentResponse> getAllDocumentsByJobApplication(Long jobApplicationId) {
        return documentRepository.getAllByJobApplicationId(jobApplicationId).orElseThrow(() -> new RuntimeException("No documents for this job application")).stream().map(documentHelper::toResponse).toList();
    }

    public Resource downloadDocument(String fileName) throws IOException {
        Document document = documentRepository.findByFileName(fileName)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        Path filePath = Paths.get("uploads/documents")
                .toAbsolutePath()
                .normalize()
                .resolve(document.getFileName())
                .normalize();


        Resource resource = new FileSystemResource(filePath);
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("File not found or not readable");
        }

        return resource;
    }

}
