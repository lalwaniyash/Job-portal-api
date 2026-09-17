package com.jobportal.job.service;

import com.jobportal.common.dto.PageResponse;
import com.jobportal.common.enums.Role;
import com.jobportal.job.dto.JobCardResponse;
import com.jobportal.job.dto.JobDetailResponse;
import com.jobportal.job.dto.JobRequest;
import com.jobportal.job.dto.JobSearchCriteria;
import com.jobportal.job.entity.Job;
import com.jobportal.job.repository.JobRepository;
import com.jobportal.job.enums.JobStatus;
import com.jobportal.job.specification.JobSpecification;
import com.jobportal.user.entity.User;
import com.jobportal.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Transactional
    public JobDetailResponse createJob(String recruiterEmail, JobRequest request) {
        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + recruiterEmail));

        if (recruiter.getRole() != Role.ROLE_RECRUITER && recruiter.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("Only recruiters and admins are allowed to post jobs.");
        }

        Job job = Job.builder()
                .title(request.getTitle())
                .companyName(request.getCompanyName())
                .location(request.getLocation())
                .isRemote(request.getIsRemote() != null ? request.getIsRemote() : false)
                .employmentType(request.getEmploymentType())
                .status(JobStatus.ACTIVE)
                .description(request.getDescription())
                .responsibilities(request.getResponsibilities())
                .qualifications(request.getQualifications())
                .skillsRequired(request.getSkillsRequired())
                .experienceRequiredYears(request.getExperienceRequiredYears() != null ? request.getExperienceRequiredYears() : 0)
                .minSalary(request.getMinSalary())
                .maxSalary(request.getMaxSalary())
                .currency(request.getCurrency() != null ? request.getCurrency() : "INR")
                .deadlineDate(request.getDeadlineDate())
                .postedBy(recruiter)
                .build();

        Job savedJob = jobRepository.save(job);
        return JobDetailResponse.fromEntity(savedJob);
    }

    @Transactional(readOnly = true)
    public JobDetailResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + id));
        return JobDetailResponse.fromEntity(job);
    }

    @Transactional(readOnly = true)
    public PageResponse<JobCardResponse> searchJobs(JobSearchCriteria criteria, Pageable pageable) {
        Page<Job> jobPage = jobRepository.findAll(JobSpecification.build(criteria), pageable);
        return PageResponse.of(jobPage.map(JobCardResponse::fromEntity));
    }

    @Transactional
    public JobDetailResponse updateJob(Long id, String recruiterEmail, JobRequest request) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + id));

        validateOwnership(job, recruiterEmail);

        job.setTitle(request.getTitle());
        job.setCompanyName(request.getCompanyName());
        job.setLocation(request.getLocation());
        job.setIsRemote(request.getIsRemote() != null ? request.getIsRemote() : false);
        job.setEmploymentType(request.getEmploymentType());
        job.setDescription(request.getDescription());
        job.setResponsibilities(request.getResponsibilities());
        job.setQualifications(request.getQualifications());
        job.setSkillsRequired(request.getSkillsRequired());
        job.setExperienceRequiredYears(request.getExperienceRequiredYears() != null ? request.getExperienceRequiredYears() : 0);
        job.setMinSalary(request.getMinSalary());
        job.setMaxSalary(request.getMaxSalary());
        job.setCurrency(request.getCurrency() != null ? request.getCurrency() : "INR");
        job.setDeadlineDate(request.getDeadlineDate());

        Job updatedJob = jobRepository.save(job);
        return JobDetailResponse.fromEntity(updatedJob);
    }

    @Transactional
    public JobDetailResponse updateJobStatus(Long id, String recruiterEmail, JobStatus status) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + id));

        validateOwnership(job, recruiterEmail);
        job.setStatus(status);

        Job updatedJob = jobRepository.save(job);
        return JobDetailResponse.fromEntity(updatedJob);
    }

    @Transactional
    public void deleteJob(Long id, String recruiterEmail) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + id));

        validateOwnership(job, recruiterEmail);
        jobRepository.delete(job);
    }

    @Transactional(readOnly = true)
    public PageResponse<JobCardResponse> getMyPostedJobs(String recruiterEmail, Pageable pageable) {
        Page<Job> jobPage = jobRepository.findByPostedByEmail(recruiterEmail, pageable);
        return PageResponse.of(jobPage.map(JobCardResponse::fromEntity));
    }

    private void validateOwnership(Job job, String recruiterEmail) {
        if (job.getPostedBy() == null || !job.getPostedBy().getEmail().equalsIgnoreCase(recruiterEmail)) {
            User user = userRepository.findByEmail(recruiterEmail).orElse(null);
            if (user == null || user.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("You are not authorized to modify this job posting.");
            }
        }
    }
}
