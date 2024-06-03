package com.springBootProject.work_tracker_management_system;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkTrackerManagementSystemApplication {

	private static final Logger log = LoggerFactory.getLogger(WorkTrackerManagementSystemApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WorkTrackerManagementSystemApplication.class, args);
		log.info("Work Tracker Application Started Successfully");
	}
}