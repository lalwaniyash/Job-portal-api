package com.jobportal.job.dto;

import com.jobportal.job.entity.Job;
import com.jobportal.job.enums.EmploymentType;
import com.jobportal.job.enums.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDetailResponse {

    private Long id;
    private String title;
    private String companyName;
    private String location;
    private Boolean isRemote;
    private EmploymentType employmentType;
    private JobStatus status;
    private String description;
    private String responsibilities;
    private String qualifications;
    private String skillsRequired;
    private Integer experienceRequiredYears;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String currency;
    private LocalDate deadlineDate;
    private Long recruiterId;
    private String recruiterName;
    private String recruiterEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static JobDetailResponse fromEntity(Job job) {
        if (job == null) return null;
        return JobDetailResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .companyName(job.getCompanyName())
                .location(job.getLocation())
                .isRemote(job.getIsRemote())
                .employmentType(job.getEmploymentType())
                .status(job.getStatus())
                .description(job.getDescription())
                .responsibilities(job.getResponsibilities())
                .qualifications(job.getQualifications())
                .skillsRequired(job.getSkillsRequired())
                .experienceRequiredYears(job.getExperienceRequiredYears())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .currency(job.getCurrency())
                .deadlineDate(job.getDeadlineDate())
                .recruiterId(job.getPostedBy() != null ? job.getPostedBy().getId() : null)
                .recruiterName(job.getPostedBy() != null ? job.getPostedBy().getName() : null)
                .recruiterEmail(job.getPostedBy() != null ? job.getPostedBy().getEmail() : null)
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }
}
