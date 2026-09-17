package com.jobportal.job.dto;

import com.jobportal.job.entity.Job;
import com.jobportal.job.enums.EmploymentType;
import com.jobportal.job.enums.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCardResponse {

    private Long id;
    private String title;
    private String companyName;
    private String location;
    private Boolean isRemote;
    private EmploymentType employmentType;
    private JobStatus status;
    private String skillsRequired;
    private Integer experienceRequiredYears;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String currency;
    private LocalDateTime createdAt;

    public static JobCardResponse fromEntity(Job job) {
        if (job == null) return null;
        return JobCardResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .companyName(job.getCompanyName())
                .location(job.getLocation())
                .isRemote(job.getIsRemote())
                .employmentType(job.getEmploymentType())
                .status(job.getStatus())
                .skillsRequired(job.getSkillsRequired())
                .experienceRequiredYears(job.getExperienceRequiredYears())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .currency(job.getCurrency())
                .createdAt(job.getCreatedAt())
                .build();
    }
}
