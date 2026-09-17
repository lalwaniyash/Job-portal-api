package com.jobportal.user.dto;

import com.jobportal.user.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private String headline;
    private String bio;
    private String skills;
    private Integer experienceYears;
    private String location;
    private String companyName;
    private String websiteUrl;
    private String linkedinUrl;
    private String githubUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProfileResponse fromEntity(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ProfileResponse.builder()
                .id(profile.getId())
                .headline(profile.getHeadline())
                .bio(profile.getBio())
                .skills(profile.getSkills())
                .experienceYears(profile.getExperienceYear())
                .location(profile.getLocation())
                .companyName(profile.getCompanyName())
                .websiteUrl(profile.getWebsiteUrl())
                .linkedinUrl(profile.getLinkedinUrl())
                .githubUrl(profile.getGithubUrl())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
} 