package com.jobportal.resume.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir:./uploads/resumes}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage directory: " + uploadDir, e);
        }
    }

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot store empty file.");
        }

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (originalFilename.contains("..")) {
            throw new IllegalArgumentException("Filename contains invalid path sequence: " + originalFilename);
        }

        String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;

        try {
            Path targetLocation = this.uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.toString();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + originalFilename + ". Please try again!", ex);
        }
    }

    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }
}
