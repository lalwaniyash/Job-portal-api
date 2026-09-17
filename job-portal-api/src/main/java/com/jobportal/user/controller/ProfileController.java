package com.jobportal.user.controller;

import com.jobportal.user.dto.ProfileRequest;
import com.jobportal.user.dto.ProfileResponse;
import com.jobportal.user.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        ProfileResponse profileResponse = profileService.getProfileByEmail(userDetails.getUsername());
        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ProfileRequest request) {
        ProfileResponse updatedProfile = profileService.createOrUpdateProfile(userDetails.getUsername(), request);
        return ResponseEntity.ok(updatedProfile);
    }
}
