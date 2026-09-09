package com.jobportal.user.service;

import com.jobportal.user.dto.ProfileRequest;
import com.jobportal.user.dto.ProfileResponse;
import com.jobportal.user.entity.Profile;
import com.jobportal.user.entity.ProfileRepository;
import com.jobportal.user.entity.User;
import com.jobportal.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ProfileResponse getProfileByEmail(String email) {
        return profileRepository.findByUserEmail(email)
                .map(ProfileResponse::fromEntity)
                .orElse(null);
    }

    @Transactional
    public ProfileResponse createOrUpdateProfile(String email, ProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Profile profile = profileRepository.findByUserEmail(email)
                .orElseGet(() -> Profile.builder().user(user).build());

        profile.setHeadline(request.getHeadline());
        profile.setBio(request.getBio());
        profile.setSkills(request.getSkills());
        profile.setExperienceYear(request.getExperienceYears());
        profile.setLocation(request.getLocation());
        profile.setCompanyName(request.getCompanyName());
        profile.setWebsiteUrl(request.getWebsiteUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());

        Profile savedProfile = profileRepository.save(profile);
        return ProfileResponse.fromEntity(savedProfile);
    }
}
