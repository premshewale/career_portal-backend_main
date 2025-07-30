package com.company.careerportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.company.careerportal.dto.CandidateRegistrationDto;
import com.company.careerportal.services.CandidateService;

@RestController
@RequestMapping("/candidate")
@CrossOrigin(origins = "http://localhost:3000")
public class CandidateController {

	@Autowired
	private CandidateService candidateService;

	@PostMapping(value = "/register", consumes = { "multipart/form-data" })
	public ResponseEntity<?> registerCandidate(@RequestPart("candidate") CandidateRegistrationDto candidateDto,
			@RequestPart(value = "resume", required = false) MultipartFile resume,
			@RequestPart(value = "photo", required = false) MultipartFile photo) {

		try {
			CandidateRegistrationDto saved = candidateService.registerCandidate(candidateDto, resume, photo);
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + ex.getMessage() + "\"}");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CandidateRegistrationDto> getCandidate(@PathVariable Long id) {
		return ResponseEntity.ok(candidateService.getCandidateById(id));
	}
	
	@PutMapping("/candidate/update/{id}")
	public ResponseEntity<CandidateRegistrationDto> updateCandidate(
	        @PathVariable Long id,
	        @RequestPart("candidate") CandidateRegistrationDto dto,
	        @RequestPart(value = "resume", required = false) MultipartFile resume,
	        @RequestPart(value = "photo", required = false) MultipartFile photo) {
	    
	    CandidateRegistrationDto updated = candidateService.updateCandidate(id, dto, resume, photo);
	    return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCandidate(@PathVariable Long id) {
		candidateService.deleteCandidate(id);
		return ResponseEntity.ok("Candidate deleted successfully");

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody CandidateRegistrationDto dto) {
		try {
			CandidateRegistrationDto candidate = candidateService.authenticate(dto.getEmail(), dto.getPassword());
			return ResponseEntity.ok(candidate);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"" + e.getMessage() + "\"}");
		}

	}

}