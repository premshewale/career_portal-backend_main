package com.company.careerportal.entity;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDate;
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
@Table(name = "users")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Transient
    private String password;

    private String name;
    private Long mobile;

    @Enumerated(EnumType.STRING)
    private StatusCandidate status;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthdate;
    private String edcation;
    private String workexp;

    @ElementCollection
    private List<String> skills;

    @Lob
    @Column(name = "resume_url", columnDefinition = "LONGTEXT")
    private String resumeUrl;

    @Lob
    @Column(name = "photo_url", columnDefinition = "LONGTEXT")
    private String photoUrl;

    public CandidateRegistrationDto toDTO() {
        return new CandidateRegistrationDto(
                this.id,
                this.username,
                this.email,
                this.password,
                this.name,
                this.mobile,
                this.status,
                this.gender,
                this.birthdate,
                this.edcation,
                this.workexp,
                this.skills,
                this.resumeUrl,
                this.photoUrl
        );
    }
}
