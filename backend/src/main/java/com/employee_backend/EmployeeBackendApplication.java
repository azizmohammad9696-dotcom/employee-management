package com.employee_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {
		"com.employee_backend",
		"com.employeecontroller",
		"com.employeemodel",
		"com.employeerepositroty",
        "com.employeeservice"
		
}
)
@EnableJpaRepositories(
		
	    basePackages = "com.employeerepositroty"
	)
@EntityScan(
	    basePackages = "com.employeemodel"
	)
public class EmployeeBackendApplication {

	public static void main(String[] args) {
        SpringApplication.run(
            EmployeeBackendApplication.class, args);
    }
}
