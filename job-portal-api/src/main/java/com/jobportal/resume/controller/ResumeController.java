package com.jobportal.resume.controller;

import com.jobportal.resume.dto.ResumeResponse;
import com.jobportal.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeResponse> uploadResume(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file) {
        ResumeResponse response = resumeService.uploadAndParseResume(userDetails.getUsername(), file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<ResumeResponse> getMyResume(@AuthenticationPrincipal UserDetails userDetails) {
        ResumeResponse response = resumeService.getResumeByEmail(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeResponse> getResumeById(@PathVariable Long id) {
        ResumeResponse response = resumeService.getResumeById(id);
        return ResponseEntity.ok(response);
    }
}
