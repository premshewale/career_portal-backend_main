package com.company.careerportal.repository;

import com.company.careerportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findByCompanyId(Long companyId);
	
}
