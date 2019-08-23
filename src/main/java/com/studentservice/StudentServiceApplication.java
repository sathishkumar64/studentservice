package com.studentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.studentservice.util.TraceSample;

@SpringBootApplication
public class StudentServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApplication.class, args);
		TraceSample.doWorkFullSampled();
	}

}

