package com.jobportal.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    @Size(max = 150, message = "Headline must be at most 150 characters")
    private String headline;

    private String bio;

    @Size(max = 500, message = "Skills must be at most 500 characters")
    private String skills;

    @Min(value = 0, message = "Experience years cannot be negative")
    private Integer experienceYears;

    private String location;

    private String companyName;

    private String websiteUrl;

    private String linkedinUrl;

    private String githubUrl;
}