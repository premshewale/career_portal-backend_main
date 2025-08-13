package com.company.careerportal.services;

import java.util.List;

import com.company.careerportal.dto.CompanyDto;
import com.company.careerportal.entity.Company;

public interface CompanyService {
	
	CompanyDto registerCompany(CompanyDto dto);

	CompanyDto login(String email, String password);

	List<CompanyDto> getAllCompanies();

	CompanyDto getCompanyById(Long id);

	CompanyDto updateCompany(Long id, CompanyDto dto);

	void deleteCompany(Long id);
}