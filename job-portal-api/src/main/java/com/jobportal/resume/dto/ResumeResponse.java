package com.jobportal.resume.dto;

import com.jobportal.resume.entity.Resume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponse {

    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String extractedText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ResumeResponse fromEntity(Resume resume) {
        if (resume == null) {
            return null;
        }
        return ResumeResponse.builder()
                .id(resume.getId())
                .fileName(resume.getFileName())
                .fileType(resume.getFileType())
                .fileSize(resume.getFileSize())
                .extractedText(resume.getExtractedText())
                .createdAt(resume.getCreatedAt())
                .updatedAt(resume.getUpdatedAt())
                .build();
    }
}
