package com.jobportal.resume.service;

import com.jobportal.resume.dto.ResumeResponse;
import com.jobportal.resume.entity.Resume;
import com.jobportal.resume.entity.ResumeRepository;
import com.jobportal.resume.parser.DocumentParser;
import com.jobportal.resume.parser.DocumentParserFactory;
import com.jobportal.user.entity.User;
import com.jobportal.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final FileStorageService fileStorageService;
    private final DocumentParserFactory parserFactory;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResumeResponse uploadAndParseResume(String email, MultipartFile file) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String contentType = file.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException("File content type cannot be empty.");
        }

        // 1. Store file on disk
        String storedFilePath = fileStorageService.storeFile(file);

        // 2. Parse text via Strategy Pattern
        String extractedText;
        try {
            DocumentParser parser = parserFactory.getParser(contentType, file.getOriginalFilename());
            extractedText = parser.parse(file.getInputStream());
        } catch (IOException | IllegalArgumentException e) {
            // Cleanup stored file if parsing fails
            fileStorageService.deleteFile(storedFilePath);
            throw new RuntimeException("Failed to parse resume document: " + e.getMessage(), e);
        }

        // 3. Upsert logic: Update existing resume or insert new
        Resume resume = resumeRepository.findByUserEmail(email)
                .orElseGet(() -> Resume.builder().user(user).build());

        // If replacing an old file, delete previous file from disk
        if (resume.getFilePath() != null && !resume.getFilePath().equals(storedFilePath)) {
            fileStorageService.deleteFile(resume.getFilePath());
        }

        resume.setFileName(file.getOriginalFilename());
        resume.setFilePath(storedFilePath);
        resume.setFileType(contentType);
        resume.setFileSize(file.getSize());
        resume.setExtractedText(extractedText);

        Resume savedResume = resumeRepository.save(resume);
        return ResumeResponse.fromEntity(savedResume);
    }

    @Transactional(readOnly = true)
    public ResumeResponse getResumeByEmail(String email) {
        return resumeRepository.findByUserEmail(email)
                .map(ResumeResponse::fromEntity)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public ResumeResponse getResumeById(Long id) {
        return resumeRepository.findById(id)
                .map(ResumeResponse::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Resume not found with id: " + id));
    }
}
