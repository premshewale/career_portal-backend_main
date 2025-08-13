			package com.company.careerportal.controller;

import com.company.careerportal.dto.JobDto;
import com.company.careerportal.dto.ApplicationDto;
import com.company.careerportal.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class JobController {

	private final JobService jobService;
	
	
	@GetMapping("/{jobId}")
	public JobDto getJobById(@PathVariable Long jobId) {
	    return jobService.getJobById(jobId);
	}
	
	
	
	
	 // new: get all jobs (for global job listings)
    @GetMapping("/all")
    public ResponseEntity<?> getAllJobs() {
        try {
            List<JobDto> jobs = jobService.getAllJobs();
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

	/**
	 * Company posts a job. Path param companyId used to link job to company.
	 * Example: POST /jobs/company/5/post
	 */
	@PostMapping("/company/{companyId}/post")
	public ResponseEntity<?> postJob(@PathVariable Long companyId, @RequestBody JobDto dto) {
		try {
			JobDto created = jobService.createJob(companyId, dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(created);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Post job failed: " + e.getMessage());
		}
	}

	/**
	 * Get all jobs for a company (company dashboard) GET /jobs/company/{companyId}
	 */
	@GetMapping("/company/{companyId}")
	public ResponseEntity<?> getCompanyJobs(@PathVariable Long companyId) {
		try {
			List<JobDto> jobs = jobService.getJobsForCompany(companyId);
			return ResponseEntity.ok(jobs);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	/**
	 * Update a job PUT /jobs/{jobId}
	 */
	@PutMapping("/{jobId}/edit")
    public ResponseEntity<?> updateJob(@PathVariable Long jobId, @RequestBody JobDto dto) {
        try {
            JobDto updated = jobService.updateJob(jobId, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update failed: " + e.getMessage());
        }
    }

	/**
	 * Delete job DELETE /jobs/{jobId}
	 */
	@DeleteMapping("/{jobId}")
	public ResponseEntity<?> deleteJob(@PathVariable Long jobId) {
		try {
			jobService.deleteJob(jobId);
			return ResponseEntity.ok("Job deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Delete failed: " + e.getMessage());
		}
	}

	/**
	 * Candidate applies to job using multipart/form-data POST /jobs/{jobId}/apply
	 * form-data: name, email, resume(file)
	 */
	@PostMapping("/{jobId}/apply")
	public ResponseEntity<?> apply(@PathVariable Long jobId,
	                                @RequestParam("name") String name,
	                                @RequestParam("email") String email,
	                                @RequestPart(value = "resume", required = false) MultipartFile resume) {
	    try {
	        ApplicationDto app = jobService.applyToJob(jobId, name, email, resume);
	        return ResponseEntity.status(HttpStatus.CREATED).body(app);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Apply failed: " + e.getMessage());
	    }
	}
	/**
	 * Get applicants for a job (company) GET /jobs/{jobId}/applicants
	 */
	@GetMapping("/{jobId}/applicants")
	public ResponseEntity<?> getApplicants(@PathVariable Long jobId) {
		try {
			List<ApplicationDto> apps = jobService.getApplicants(jobId);
			return ResponseEntity.ok(apps);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error fetching applicants: " + e.getMessage());
		}
	}
				
	/**
	 * Download resume bytes GET /jobs/applications/{appId}/resume
	 */
	@GetMapping("/applications/{appId}/resume")
	public ResponseEntity<byte[]> downloadResume(@PathVariable Long appId) {
		try {
			byte[] data = jobService.downloadResume(appId);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename("resume").build());
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(data);
		} catch (Exception e) {
			// returning 404 with error message would normally be JSON; for binary endpoint,
			// return 404.
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
