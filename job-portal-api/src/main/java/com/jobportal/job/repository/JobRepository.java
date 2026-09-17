package com.jobportal.job.repository;

import com.jobportal.job.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    Page<Job> findByPostedByEmail(String email, Pageable pageable);

    Page<Job> findByPostedById(Long recruiterId, Pageable pageable);

    long countByPostedById(Long recruiterId);
}
