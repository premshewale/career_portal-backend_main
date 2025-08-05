package com.company.careerportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {
    private Long id;
    private String username;
    private String email;
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
