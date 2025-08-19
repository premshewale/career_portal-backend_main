package com.company.careerportal.servicesImpl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.careerportal.dto.CandidateRegistrationDto;
import com.company.careerportal.entity.Candidate;
import com.company.careerportal.repository.CandidateRepository;
import com.company.careerportal.services.CandidateService;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;

	@Override
	public CandidateRegistrationDto registerCandidate(CandidateRegistrationDto dto, MultipartFile resume,
			MultipartFile photo) {
		if (candidateRepository.existsByEmail(dto.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		try {
			if (resume != null && !resume.isEmpty()) {
				dto.setResume(Base64.getEncoder().encodeToString(resume.getBytes()));
			}
			if (photo != null && !photo.isEmpty()) {
				dto.setPhoto(Base64.getEncoder().encodeToString(photo.getBytes()));
			}
		} catch (IOException e) {
			throw new RuntimeException("Error processing uploaded files: " + e.getMessage());
		}

		Candidate saved = candidateRepository.save(dto.toEntity());
		return saved.toDTO();
	}

	@Override
	public CandidateRegistrationDto updateCandidate(Long id, CandidateRegistrationDto dto, MultipartFile resume,
			MultipartFile photo) {
		Candidate candidate = candidateRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Candidate not found"));

		updateFields(candidate, dto);

		try {
			if (resume != null && !resume.isEmpty()) {
				candidate.setResume(resume.getBytes());
			}

			if (photo != null && !photo.isEmpty()) {
				candidate.setPhoto(photo.getBytes());
			}
		} catch (IOException e) {
			throw new RuntimeException("Error processing uploaded files: " + e.getMessage());
		}

		return candidateRepository.save(candidate).toDTO();
	}

	@Override
	public CandidateRegistrationDto getCandidateById(Long id) {
		Candidate candidate = candidateRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Candidate not found"));
		return candidate.toDTO();
	}

	@Override
	public List<CandidateRegistrationDto> getAllCandidates() {
		return candidateRepository.findAll().stream().map(Candidate::toDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteCandidate(Long id) {
		Candidate candidate = candidateRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Candidate not found"));
		candidateRepository.delete(candidate);
	}

	private void updateFields(Candidate candidate, CandidateRegistrationDto dto) {
		candidate.setUsername(dto.getUsername());
		candidate.setEmail(dto.getEmail());
		candidate.setPassword(dto.getPassword());
		candidate.setName(dto.getName());
		candidate.setMobile(dto.getMobile());
		candidate.setStatus(dto.getStatus());
		candidate.setGender(dto.getGender());
		candidate.setBirthdate(dto.getBirthdate());
		candidate.setEducation(dto.getEducation());
		candidate.setWorkexp(dto.getWorkexp());
		candidate.setSkills(dto.getSkills());

		if (dto.getResume() != null)
			candidate.setResume(Base64.getDecoder().decode(dto.getResume()));
		if (dto.getPhoto() != null)
			candidate.setPhoto(Base64.getDecoder().decode(dto.getPhoto()));
	}

	@Override
	public CandidateRegistrationDto authenticate(String email, String password) {
		Optional<Candidate> optionalCandidate = candidateRepository.findByEmail(email);

		if (optionalCandidate.isPresent()) {
			Candidate candidate = optionalCandidate.get();
			if (candidate.getPassword().equals(password)) {
				return candidate.toDTO();
			} else {
				throw new RuntimeException("Invalid credentials");
			}
		} else {
			throw new RuntimeException("Candidate not found");
		}
	}

}
