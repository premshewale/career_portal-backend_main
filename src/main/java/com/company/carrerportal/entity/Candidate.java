package com.company.carrerportal.entity;


import java.time.LocalDate;
import java.util.List;

import com.company.carrerportal.enums.Gender;
import com.company.carrerportal.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Candidate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	
	@Email
	@Column(name = "email", nullable = false, unique = true)
	private String	email;
	@Transient
	
	private String password;
	private String name;
	private int mobile;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private LocalDate birthdate;
	private String edcation;
	private String workexp;
	
	// skills
	@ElementCollection
	private List<String> skills;

	
	@Lob
	@Column(name = "resume_url", columnDefinition = "LONGTEXT")
	private String resumeUrl;
	

	@Lob
	@Column(name = "photo_url", columnDefinition = "LONGTEXT")
	private String photoUrl;
	
		
	
	
	
}
