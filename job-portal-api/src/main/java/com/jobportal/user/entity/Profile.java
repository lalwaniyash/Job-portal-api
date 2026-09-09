package com.jobportal.user.entity;

import com.jobportal.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "profiles")
public class Profile extends BaseEntity {
    @Column(length=150)
    private String headline;
    @Column(columnDefinition = "TEXT")
    private String bio;
    @Column(length = 500)
    private String skills;
    @Column(name = "experience_years")
    private Integer experienceYear;
    @Column(length = 100)
    private String location;
    @Column(name = "company_name",length = 100)
    private String companyName;
    @Column(name = "website_url")
    private String websiteUrl;
    @Column(name = "linkedin_url")
    private String linkedinUrl;
    @Column(name = "github_url")
    private String githubUrl;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
