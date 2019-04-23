package com.studentservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.studentservice.domain.Student;
import com.studentservice.domain.StudentAppData;
import com.studentservice.repositories.MongoDBStudentRepository;

@RestController
@RequestMapping(path = "/api")
public class StudentServiceController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public MongoDBStudentRepository mongoDBStudentRepository;

	@GetMapping(path = "/student/getStudentDetailsForSchool/{schoolname}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppData getStudents(@PathVariable String schoolname, @RequestHeader HttpHeaders headers) throws Exception {
		logger.info("Reading Header Info ::::::::: {}", headers);
		StudentAppData appData = null;	
		if (headers.getFirst("end-user") == null || headers.getFirst("end-user").isEmpty()) {				
			throw new IllegalArgumentException("The 'end user' parameter must not be null or empty");			
		} else {				
			logger.info("Getting Student details for {} along with enduser ::::::::: {}",schoolname,headers.getFirst("end-user"));				
		}
		appData = mongoDBStudentRepository.findByschoolname(schoolname);
		return appData;
	}

	@GetMapping(path = "/student/all")
	public @ResponseBody List<Student> retriveStudent(@RequestHeader HttpHeaders headers) {
		logger.info("Retrieving all students {} with headers.....", headers);
		List<Student> studentList = mongoDBStudentRepository.findAll();
		return studentList;
	}

	@PostMapping(path = "/student/createstudent")
	public StudentAppData saveStudent(@RequestBody Student student) {
		logger.info("Posting Student details ................");
		StudentAppData appData = null;
		appData = mongoDBStudentRepository.save(student);
		return appData;
	}
}
