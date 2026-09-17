package com.jobportal.job.dto;

import com.jobportal.job.enums.EmploymentType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {

    @NotBlank(message = "Job title is required")
    @Size(max = 150, message = "Title must not exceed 150 characters")
    private String title;

    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name must not exceed 100 characters")
    private String companyName;

    @NotBlank(message = "Location is required")
    @Size(max = 100, message = "Location must not exceed 100 characters")
    private String location;

    @Builder.Default
    private Boolean isRemote = false;

    @NotNull(message = "Employment type is required")
    private EmploymentType employmentType;

    @NotBlank(message = "Job description is required")
    private String description;

    private String responsibilities;

    private String qualifications;

    @NotBlank(message = "Required skills are required")
    @Size(max = 500, message = "Skills must not exceed 500 characters")
    private String skillsRequired;

    @Min(value = 0, message = "Experience required years cannot be negative")
    private Integer experienceRequiredYears;

    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String currency;
    private LocalDate deadlineDate;
}
