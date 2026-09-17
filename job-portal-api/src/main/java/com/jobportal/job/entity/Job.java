package com.jobportal.job.entity;

import com.jobportal.common.entity.BaseEntity;
import com.jobportal.job.enums.EmploymentType;
import com.jobportal.job.enums.JobStatus;
import com.jobportal.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "jobs", indexes = {
        @Index(name = "idx_jobs_status", columnList = "status"),
        @Index(name = "idx_jobs_location", columnList = "location"),
        @Index(name = "idx_jobs_employment_type", columnList = "employment_type"),
        @Index(name = "idx_jobs_created_at", columnList = "created_at")
})
public class Job extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(nullable = false, length = 100)
    private String location;

    @Builder.Default
    @Column(name = "is_remote", nullable = false)
    private Boolean isRemote = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false, length = 30)
    private EmploymentType employmentType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private JobStatus status = JobStatus.ACTIVE;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String responsibilities;

    @Column(columnDefinition = "TEXT")
    private String qualifications;

    @Column(name = "skills_required", nullable = false, length = 500)
    private String skillsRequired;

    @Builder.Default
    @Column(name = "experience_required_years", nullable = false)
    private Integer experienceRequiredYears = 0;

    @Column(name = "min_salary", precision = 12, scale = 2)
    private BigDecimal minSalary;

    @Column(name = "max_salary", precision = 12, scale = 2)
    private BigDecimal maxSalary;

    @Builder.Default
    @Column(length = 10)
    private String currency = "INR";

    @Column(name = "deadline_date")
    private LocalDate deadlineDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User postedBy;
}
