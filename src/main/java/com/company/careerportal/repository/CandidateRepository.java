package com.company.careerportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.careerportal.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    boolean existsByEmail(String email);
}
