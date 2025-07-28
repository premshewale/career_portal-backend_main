package com.company.careerportal.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.careerportal.dto.CandidateRegistrationDto;
import com.company.careerportal.entity.Candidate;
import com.company.careerportal.repository.CandidateRepository;
import com.company.careerportal.services.CandidateService;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;

	@Override
	public CandidateRegistrationDto registerUser(CandidateRegistrationDto candidateRegistrationDto) {

		// Check if email already exists
		if (candidateRepository.existsByEmail(candidateRegistrationDto.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		// Save the candidate
		Candidate saved = candidateRepository.save(candidateRegistrationDto.toEntity());

		// Convert entity to DTO and return
		return saved.toDTO();
	}
}
