package com.spring.job_tracker.ai.services;

import com.spring.job_tracker.models.Document;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class ResumeTextExtractorService {

    private final Tika tika;

    public ResumeTextExtractorService(Tika tika) {
        this.tika = tika;
    }

    public String extractText(Document document) {
        if (document == null || document.getFileKey() == null) {
            return "";
        }

        Path filePath = Paths.get("uploads/documents")
                .toAbsolutePath()
                .normalize()
                .resolve(document.getFileName())
                .normalize();

        if (!Files.exists(filePath)) {
            return "";
        }

        String filename = document.getFileName().toLowerCase();

        try {
            if (filename.endsWith(".pdf")) {
                try (PDDocument pdf = Loader.loadPDF(filePath.toFile())) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    String text = stripper.getText(pdf);
                    System.out.println("PDFBox extracted length: " + text.length());
                    return text;
                }
            }

            // Tika fallback for docx/other formats
            try (InputStream stream = Files.newInputStream(filePath)) {
                Metadata metadata = new Metadata();
                metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, filePath.getFileName().toString());
                return tika.parseToString(stream, metadata);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read document file: " + document.getFileKey(), e);
        } catch (TikaException e) {
            throw new RuntimeException("Failed to parse document content: " + document.getFileKey(), e);
        }
    }
}

