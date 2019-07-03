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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="COE Student -Istio Implementation", description="Operations pertaining to Student Service")
@RestController
@RequestMapping(path = "/api")
public class StudentServiceController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public MongoDBStudentRepository mongoDBStudentRepository;

	@ApiOperation(value = "Search a Student List with an School Name",response = String.class)
	@GetMapping(path = "/student/getStudentDetailsForSchool/{schoolname}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppData getStudents(@PathVariable String schoolname, @RequestHeader HttpHeaders headers)
			throws Exception {
		logger.info("Reading Header Info ::::::::: {}", headers);
		StudentAppData appData = null;		
		logger.info("Reading Header Info ::::::::: {}",headers);		
		String endUser=headers.getFirst("end-user") != null ? headers.getFirst("end-user") :"No End User";		
		logger.info("Getting Student details for {} along with enduser ::::::::: {}",schoolname,endUser);				
		appData=mongoDBStudentRepository.findByschoolname(schoolname);		
		return appData;
	}

	@ApiOperation(value = "View a list of available students", response = Iterable.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping(path = "/student/all")
	public @ResponseBody List<Student> retriveStudent(@RequestHeader HttpHeaders headers) {
		logger.info("Retrieving all students {} with headers.....", headers);
		List<Student> studentList = mongoDBStudentRepository.findAll();
		return studentList;
	}

	@ApiOperation(value = "Add a new Student")
	@PostMapping(path = "/student/createstudent")
	public StudentAppData saveStudent(@RequestBody Student student) {
		logger.info("Posting Student details ................");
		StudentAppData appData = null;
		appData = mongoDBStudentRepository.save(student);
		return appData;
	}
}
