package com.jobportal.resume.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Optional<Resume> findByUserEmail(String email);

    Optional<Resume> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
