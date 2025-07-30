package com.company.careerportal.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.company.careerportal.dto.CandidateRegistrationDto;

public interface CandidateService {
	CandidateRegistrationDto registerCandidate(CandidateRegistrationDto dto, MultipartFile resume, MultipartFile photo);

	CandidateRegistrationDto updateCandidate(Long id, CandidateRegistrationDto dto, MultipartFile resume, MultipartFile photo);

	CandidateRegistrationDto getCandidateById(Long id);

	List<CandidateRegistrationDto> getAllCandidates();

	void deleteCandidate(Long id);

	CandidateRegistrationDto authenticate(String email, String password);
}
