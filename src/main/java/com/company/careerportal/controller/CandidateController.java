package com.company.careerportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.careerportal.dto.CandidateRegistrationDto;
import com.company.careerportal.services.CandidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CandidateController {

	@Autowired
	private CandidateService candidateService;

	// Register a candidate
	@PostMapping("/register")
	public ResponseEntity<?> registerCandidate(@RequestBody CandidateRegistrationDto candidateRegistrationDto) {
		try {
			CandidateRegistrationDto saved = candidateService.registerUser(candidateRegistrationDto);
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + ex.getMessage() + "\"}");
		}
	}

	
	@GetMapping("/test")
	public ResponseEntity<String> testApi() {
		return ResponseEntity.ok("API is working!");
	}
}
