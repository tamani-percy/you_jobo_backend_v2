package com.spring.job_tracker.controllers;

import com.spring.job_tracker.dtos.requests.DocumentRequest;
import com.spring.job_tracker.dtos.responses.DocumentResponse;
import com.spring.job_tracker.services.DocumentService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName) throws IOException {
        Resource resource = documentService.downloadDocument(fileName);

        String contentType = Files.probeContentType(resource.getFile().toPath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponse> createDocument(
            @RequestParam("documentRequest") String documentRequestJson,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        DocumentRequest documentRequest = new ObjectMapper()
                .readValue(documentRequestJson, DocumentRequest.class);

        return ResponseEntity.ok(documentService.createDocument(documentRequest, file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentService.getDocument(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDocument(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentService.deleteDocument(id));
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponse> updateDocument(
            @RequestPart("documentRequest") String documentRequestJson,
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        DocumentRequest documentRequest = new ObjectMapper()
                .readValue(documentRequestJson, DocumentRequest.class);
        return ResponseEntity.ok(documentService.updateDocument(documentRequest, file));
    }

    @GetMapping("/job-application/{id}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsByJobApplication(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentService.getAllDocumentsByJobApplication(id));
    }

    @GetMapping("/preview")
    public ResponseEntity<Resource> previewDocument(@RequestParam String fileName) throws MalformedURLException {
        Path filePath = Paths.get("uploads/documents")
                .resolve(fileName)
                .normalize();

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
