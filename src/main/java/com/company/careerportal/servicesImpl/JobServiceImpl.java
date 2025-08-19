package com.company.careerportal.servicesImpl;

import com.company.careerportal.dto.JobDto;
import com.company.careerportal.dto.ApplicationDto;
import com.company.careerportal.entity.Job;
import com.company.careerportal.entity.Application;
import com.company.careerportal.entity.Company;
import com.company.careerportal.repository.JobRepository;
import com.company.careerportal.repository.ApplicationRepository;
import com.company.careerportal.repository.CompanyRepository;
import com.company.careerportal.services.JobService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepository;
	private final ApplicationRepository applicationRepository;
	private final CompanyRepository companyRepository;

	@Override
	public JobDto createJob(Long companyId, JobDto dto) {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));

		Job job = new Job();
		job.setTitle(dto.title);
		job.setDescription(dto.description);
		job.setLocation(dto.location);
		job.setSalary(dto.salary);
		job.setPostedAt(LocalDateTime.now());
		job.setCompany(company);

		Job saved = jobRepository.save(job);
		return toJobDto(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobDto> getJobsForCompany(Long companyId) {
		List<Job> jobs = jobRepository.findByCompanyId(companyId);
		return jobs.stream().map(this::toJobDto).collect(Collectors.toList());
	}

	@Override
	public void deleteJob(Long jobId) {
		if (!jobRepository.existsById(jobId)) {
			throw new RuntimeException("Job not found with id: " + jobId);
		}

		List<Application> apps = applicationRepository.findByJobId(jobId);
		if (!apps.isEmpty()) {
			applicationRepository.deleteAll(apps);
		}
		jobRepository.deleteById(jobId);
	}

	@Override
	public JobDto updateJob(Long jobId, JobDto dto) {
		Job job = jobRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

		if (dto.title != null)
			job.setTitle(dto.title);
		if (dto.description != null)
			job.setDescription(dto.description);
		if (dto.location != null)
			job.setLocation(dto.location);
		if (dto.salary != null)
			job.setSalary(dto.salary);

		Job saved = jobRepository.save(job);
		return toJobDto(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ApplicationDto> getApplicants(Long jobId) {
		// verify job exists
		if (!jobRepository.existsById(jobId)) {
			throw new RuntimeException("Job not found with id: " + jobId);
		}
		List<Application> apps = applicationRepository.findByJobId(jobId);
		return apps.stream().map(this::toApplicationDto).collect(Collectors.toList());
	}

	@Override
	public ApplicationDto applyToJob(Long jobId, String name, String email, MultipartFile resume) throws Exception {
		Job job = jobRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

		Application app = new Application();
		app.setCandidateName(name);
		app.setCandidateEmail(email);
		app.setAppliedAt(LocalDateTime.now());
		app.setJob(job);

		if (resume != null && !resume.isEmpty()) {

			app.setResume(resume.getBytes());
			String fname = resume.getOriginalFilename();
			app.setResumeFilename(fname != null ? fname : "resume");
		}

		Application saved = applicationRepository.save(app);
		return toApplicationDto(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public byte[] downloadResume(Long applicationId) {
		Application app = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));
		if (app.getResume() == null)
			throw new RuntimeException("No resume uploaded for application id: " + applicationId);
		return app.getResume();
	}

	@Override
	@Transactional(readOnly = true)
	public JobDto getJobById(Long jobId) {
		Job job = jobRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));
		return toJobDto(job);
	}

	@Override
	public List<ApplicationDto> getApplicantsByCompany(Long companyId) {
		List<Job> companyJobs = jobRepository.findByCompanyId(companyId);

		List<Long> jobIds = companyJobs.stream().map(Job::getId).collect(Collectors.toList());

		List<Application> applications = applicationRepository.findByJobIdIn(jobIds);

		return applications.stream().map(this::toApplicationDto)

				.collect(Collectors.toList());
	}

	/* ----------------- Helpers ----------------- */

	private JobDto toJobDto(Job job) {
		JobDto dto = new JobDto();
		dto.id = job.getId();
		dto.title = job.getTitle();
		dto.description = job.getDescription();
		dto.location = job.getLocation();
		dto.salary = job.getSalary();
		dto.postedAt = job.getPostedAt();
		if (job.getCompany() != null)
			dto.companyId = job.getCompany().getId();
		return dto;
	}

	private ApplicationDto toApplicationDto(Application app) {
		ApplicationDto dto = new ApplicationDto();
		dto.id = app.getId();
		dto.candidateName = app.getCandidateName();
		dto.candidateEmail = app.getCandidateEmail();
		dto.resumeFilename = app.getResumeFilename();
		dto.jobId = app.getJob() != null ? app.getJob().getId() : null;
		dto.appliedAt = app.getAppliedAt();
		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobDto> getAllJobs() {
		return jobRepository.findAll().stream().map(this::toJobDto).collect(Collectors.toList());
	}
}
