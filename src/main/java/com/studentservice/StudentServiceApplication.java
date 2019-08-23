package com.studentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentServiceApplication {
	
	//private static final String PROJECT_ID = "sapient-si-dsst-184990";
	
	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApplication.class, args);
		//try {
		//	TraceSample.createAndRegisterGoogleCloudPlatform(PROJECT_ID);
		//	TraceSample.createAndRegister();
		//	TraceSample.doWork();
		
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
	}

}

