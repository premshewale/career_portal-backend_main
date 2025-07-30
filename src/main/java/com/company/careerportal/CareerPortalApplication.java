package com.company.careerportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.company.carrerportal.repository")
//@EntityScan(basePackages = "com.company.carrerportal.entity")
//@ComponentScan(basePackages = "com.company.carrerportal")
public class CareerPortalApplication {
	public static void main(String[] args) {
		SpringApplication.run(CareerPortalApplication.class, args);
	}
}
