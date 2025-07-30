package com.company.careerportal.dto;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import com.company.careerportal.entity.Candidate;
import com.company.careerportal.enums.Gender;
import com.company.careerportal.enums.StatusCandidate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRegistrationDto {
	private Long id;
	private String username;
	private String email;
	private String password;
	private String name;
	private Long mobile;
	private StatusCandidate status;
	private Gender gender;
	private LocalDate birthdate;
	private String education;
	private String workexp;
	private List<String> skills;
	private String resume;
	private String photo;
	

	public Candidate toEntity() {
		return new Candidate(this.id, this.username, this.email, this.password, this.name, this.mobile, this.status,
				this.gender, this.birthdate, this.education, this.workexp, this.skills,
				resume != null ? Base64.getDecoder().decode(resume) : null,
				photo != null ? Base64.getDecoder().decode(photo) : null);

	}

}
