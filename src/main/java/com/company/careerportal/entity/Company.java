package com.company.careerportal.entity;

import java.time.LocalDate;
import java.util.List;

import com.company.careerportal.enums.Gender;
import com.company.careerportal.enums.StatusCandidate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Email
	@Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private String companyName;
    private String industry;
    private String companySize;
    private String headquarters;
    private String companyType;
    private String founded;
    private String specialties;
    private String address;
    private String companyPhone;
}
