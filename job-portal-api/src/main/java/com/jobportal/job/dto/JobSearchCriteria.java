package com.jobportal.job.dto;

import com.jobportal.job.enums.EmploymentType;
import com.jobportal.job.enums.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchCriteria {

    private String keyword;
    private String location;
    private Boolean isRemote;
    private EmploymentType employmentType;
    private JobStatus status;
    private String skill;
    private Integer maxExperienceYears;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
}
