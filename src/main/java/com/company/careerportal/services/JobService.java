package com.company.careerportal.services;

import com.company.careerportal.dto.JobDto;
import com.company.careerportal.dto.ApplicationDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface JobService {
    JobDto createJob(Long companyId, JobDto dto);
    List<JobDto> getJobsForCompany(Long companyId);
    void deleteJob(Long jobId);
    JobDto updateJob(Long jobId, JobDto dto);
    List<ApplicationDto> getApplicants(Long jobId);
    ApplicationDto applyToJob(Long jobId, String name, String email, MultipartFile resume) throws Exception;
    byte[] downloadResume(Long applicationId);
    
    List<JobDto> getAllJobs();
    
	JobDto getJobById(Long jobId);
}
