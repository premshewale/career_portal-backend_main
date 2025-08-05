package com.company.careerportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.careerportal.entity.Company;
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByEmail(String email);
    Optional<Company> findByEmail(String email); // cleaner and safer
}
	
