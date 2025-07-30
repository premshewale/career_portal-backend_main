package com.company.careerportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.careerportal.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
	boolean existsByEmail(String email);

	Optional<Candidate> findByEmail(String email);
}
