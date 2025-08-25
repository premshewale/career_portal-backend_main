package com.company.careerportal.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.careerportal.dto.CompanyDto;
import com.company.careerportal.entity.Company;
import com.company.careerportal.repository.CompanyRepository;
import com.company.careerportal.services.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder; // ✅ inject encoder

    @Override
    public CompanyDto registerCompany(CompanyDto dto) {
        Company company = mapToEntity(dto);

        // ✅ hash password before saving
        company.setPassword(passwordEncoder.encode(dto.getPassword()));

        Company saved = companyRepository.save(company);
        return mapToDto(saved);
    }

    @Override
    public CompanyDto login(String email, String rawPassword) {
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // ✅ match raw password with stored hash
        if (passwordEncoder.matches(rawPassword, company.getPassword())) {
            return mapToDto(company);
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return mapToDto(company);
    }

    @Override
    public CompanyDto updateCompany(Long id, CompanyDto dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setUsername(dto.getUsername());
        company.setEmail(dto.getEmail());
        company.setCompanyName(dto.getCompanyName());
        company.setIndustry(dto.getIndustry());
        company.setCompanySize(dto.getCompanySize());
        company.setHeadquarters(dto.getHeadquarters());
        company.setCompanyType(dto.getCompanyType());
        company.setFounded(dto.getFounded());
        company.setSpecialties(dto.getSpecialties());
        company.setAddress(dto.getAddress());
        company.setCompanyPhone(dto.getCompanyPhone());

        // ✅ if password is updated, re-hash it
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            company.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return mapToDto(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    private CompanyDto mapToDto(Company entity) {
        CompanyDto dto = new CompanyDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword()); // ⚠️ hashed password will be returned
        dto.setCompanyName(entity.getCompanyName());
        dto.setIndustry(entity.getIndustry());
        dto.setCompanySize(entity.getCompanySize());
        dto.setHeadquarters(entity.getHeadquarters());
        dto.setCompanyType(entity.getCompanyType());
        dto.setFounded(entity.getFounded());
        dto.setSpecialties(entity.getSpecialties());
        dto.setAddress(entity.getAddress());
        dto.setCompanyPhone(entity.getCompanyPhone());
        return dto;
    }

    private Company mapToEntity(CompanyDto dto) {
        Company entity = new Company();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword()); // ⚠️ will be re-encoded in register/update
        entity.setCompanyName(dto.getCompanyName());
        entity.setIndustry(dto.getIndustry());
        entity.setCompanySize(dto.getCompanySize());
        entity.setHeadquarters(dto.getHeadquarters());
        entity.setCompanyType(dto.getCompanyType());
        entity.setFounded(dto.getFounded());
        entity.setSpecialties(dto.getSpecialties());
        entity.setAddress(dto.getAddress());
        entity.setCompanyPhone(dto.getCompanyPhone());
        return entity;
    }
}
