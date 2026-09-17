package com.jobportal.job.controller;

import com.jobportal.common.dto.PageResponse;
import com.jobportal.job.dto.JobCardResponse;
import com.jobportal.job.dto.JobDetailResponse;
import com.jobportal.job.dto.JobRequest;
import com.jobportal.job.dto.JobSearchCriteria;
import com.jobportal.job.enums.JobStatus;
import com.jobportal.job.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // --- Public Endpoints ---

    @GetMapping
    public ResponseEntity<PageResponse<JobCardResponse>> searchJobs(
            @ModelAttribute JobSearchCriteria criteria,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<JobCardResponse> response = jobService.searchJobs(criteria, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDetailResponse> getJobById(@PathVariable Long id) {
        JobDetailResponse response = jobService.getJobById(id);
        return ResponseEntity.ok(response);
    }

    // --- Recruiter & Admin Protected Endpoints ---

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER', 'ROLE_ADMIN')")
    public ResponseEntity<JobDetailResponse> createJob(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody JobRequest request) {
        JobDetailResponse response = jobService.createJob(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER', 'ROLE_ADMIN')")
    public ResponseEntity<JobDetailResponse> updateJob(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody JobRequest request) {
        JobDetailResponse response = jobService.updateJob(id, userDetails.getUsername(), request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER', 'ROLE_ADMIN')")
    public ResponseEntity<JobDetailResponse> updateJobStatus(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("status") JobStatus status) {
        JobDetailResponse response = jobService.updateJobStatus(id, userDetails.getUsername(), status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        jobService.deleteJob(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER', 'ROLE_ADMIN')")
    public ResponseEntity<PageResponse<JobCardResponse>> getMyPostedJobs(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<JobCardResponse> response = jobService.getMyPostedJobs(userDetails.getUsername(), pageable);
        return ResponseEntity.ok(response);
    }
}
