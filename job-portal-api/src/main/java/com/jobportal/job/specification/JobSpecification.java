package com.jobportal.job.specification;

import com.jobportal.job.dto.JobSearchCriteria;
import com.jobportal.job.entity.Job;
import com.jobportal.job.enums.JobStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class JobSpecification {

    public static Specification<Job> build(JobSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Status Filter (Default to ACTIVE if not specified)
            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            } else {
                predicates.add(cb.equal(root.get("status"), JobStatus.ACTIVE));
            }

            // 2. Keyword Search (Matches across title, companyName, description)
            if (StringUtils.hasText(criteria.getKeyword())) {
                String pattern = "%" + criteria.getKeyword().toLowerCase().trim() + "%";
                Predicate titleMatch = cb.like(cb.lower(root.get("title")), pattern);
                Predicate companyMatch = cb.like(cb.lower(root.get("companyName")), pattern);
                Predicate descMatch = cb.like(cb.lower(root.get("description")), pattern);
                predicates.add(cb.or(titleMatch, companyMatch, descMatch));
            }

            // 3. Location Filter
            if (StringUtils.hasText(criteria.getLocation())) {
                String pattern = "%" + criteria.getLocation().toLowerCase().trim() + "%";
                predicates.add(cb.like(cb.lower(root.get("location")), pattern));
            }

            // 4. Remote Filter
            if (criteria.getIsRemote() != null) {
                predicates.add(cb.equal(root.get("isRemote"), criteria.getIsRemote()));
            }

            // 5. Employment Type Filter
            if (criteria.getEmploymentType() != null) {
                predicates.add(cb.equal(root.get("employmentType"), criteria.getEmploymentType()));
            }

            // 6. Skill Filter
            if (StringUtils.hasText(criteria.getSkill())) {
                String pattern = "%" + criteria.getSkill().toLowerCase().trim() + "%";
                predicates.add(cb.like(cb.lower(root.get("skillsRequired")), pattern));
            }

            // 7. Experience Filter
            if (criteria.getMaxExperienceYears() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("experienceRequiredYears"), criteria.getMaxExperienceYears()));
            }

            // 8. Min Salary Filter
            if (criteria.getMinSalary() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("maxSalary"), criteria.getMinSalary()));
            }

            // 9. Max Salary Filter
            if (criteria.getMaxSalary() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("minSalary"), criteria.getMaxSalary()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
