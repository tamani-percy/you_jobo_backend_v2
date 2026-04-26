package com.spring.job_tracker.helpers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Component
public class FileHelper {

    private static final String UPLOAD_DIR = "uploads/documents";

    public String uploadFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalName = file.getOriginalFilename();
        String extension = getExtension(originalName);
        String fileName = UUID.randomUUID() + extension;

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName; // store this in DB
    }
    public void deleteFile(String fileUrl) {
        try {
            // Convert URL → file path
            String relativePath = fileUrl.replace("/uploads/", "uploads/");
            Path filePath = Paths.get(relativePath);

            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // You may want to log instead of throwing
            throw new RuntimeException("Failed to delete file: " + fileUrl, e);
        }
    }


    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf("."));
    }
}