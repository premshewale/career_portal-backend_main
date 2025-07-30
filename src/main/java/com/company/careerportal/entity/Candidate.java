package com.company.careerportal.entity;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.company.careerportal.dto.CandidateRegistrationDto;
import com.company.careerportal.enums.Gender;
import com.company.careerportal.enums.StatusCandidate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@Email
	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	private String name;
	private Long mobile;

	@Enumerated(EnumType.STRING)
	private StatusCandidate status;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private LocalDate birthdate;
	private String education;
	private String workexp;

	@ElementCollection
	private List<String> skills;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] resume;

	@Lob

	@Column(columnDefinition = "LONGBLOB")
	private byte[] photo;

	public CandidateRegistrationDto toDTO() {
		return new CandidateRegistrationDto(this.id, this.username, this.email, this.password, this.name, this.mobile,
				this.status, this.gender, this.birthdate, this.education, this.workexp, this.skills,
				resume != null ? Base64.getEncoder().encodeToString(resume) : null,
				photo != null ? Base64.getEncoder().encodeToString(photo) : null);

	}
}