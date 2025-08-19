package com.company.careerportal.controller;

import com.company.careerportal.dto.CompanyDto;
import com.company.careerportal.services.CompanyService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {

	private final CompanyService companyService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody CompanyDto dto) {
		try {
			return ResponseEntity.ok(companyService.registerCompany(dto));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody CompanyDto dto) {
		System.out.println("Company login attempt for email: " + dto.getEmail());
		try {
			CompanyDto result = companyService.login(dto.getEmail(), dto.getPassword());
			return ResponseEntity.ok(result);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login error");
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCompanies() {
		try {
			List<CompanyDto> companies = companyService.getAllCompanies();
			return ResponseEntity.ok(companies);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error fetching companies: " + e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(companyService.getCompanyById(id));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Company not found: " + e.getMessage());
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CompanyDto dto) {
		try {
			return ResponseEntity.ok(companyService.updateCompany(id, dto));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Update failed: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			companyService.deleteCompany(id);
			return ResponseEntity.ok("Company deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Delete failed: " + e.getMessage());
		}
	}
}
