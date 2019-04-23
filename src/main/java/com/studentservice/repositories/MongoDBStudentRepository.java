package com.studentservice.repositories;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.studentservice.domain.Student;
import com.studentservice.domain.StudentAppData;

@Repository
public class MongoDBStudentRepository implements StudentRepository{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final MongoOperations operations;	

	@Autowired
	BuildProperties buildProperties;

	//@Autowired
   // private JmsTemplate jmsTemplate;
	
	
	//@Autowired
   // private Queue queue;
	
	
	@Autowired
	public MongoDBStudentRepository(MongoOperations operations) {	
		this.operations = operations;
	}
				
	@Override
	public StudentAppData save(Student student) {
		StudentAppData appData=new StudentAppData();			
		operations.save(student);
		String buildInfo=setBuildInfo();		
		appData.setStudentAppInfo(buildInfo);		
		appData.setMessage("Student data successuflly saved.");		
		return appData;
	}

	

	@Override
	public StudentAppData findByschoolname(String schoolName) {
		List<Student> studentList=null;			
		StudentAppData appData=new StudentAppData();			
		//sendJmsMessage(schoolName);		
		Query query = query(where("schoolname").is(schoolName));		
		studentList=operations.find(query, Student.class);
		String buildInfo=setBuildInfo();		
		appData.setStudentAppInfo(buildInfo);
		if (studentList.isEmpty()) {				
			appData.setMessage("Student List Not Found");	
		}else{				 
			appData.setListStudent(studentList);
		}
		return appData;
	}

	@Override
	public List<Student> findAll() {	
		List<Student> studentList=null;
		studentList = operations.findAll(Student.class);
		return studentList;
	}

	
	private void sendJmsMessage(String schoolname){		
		 Instant instant = Instant.now(); 		
		 StringBuilder sendingMessage=new StringBuilder();
		 sendingMessage.append("Posting JMS Student details for ");
		 sendingMessage.append(schoolname);
		 sendingMessage.append(" and time is ");
		 sendingMessage.append(instant);
		// jmsTemplate.convertAndSend(queue,sendingMessage.toString());		
		 logger.info("JMS Posting Message............... {} ",sendingMessage.toString());	
	}
	
	
	private String setBuildInfo(){		
		StringBuilder builder=new StringBuilder();
		builder.append("Application Name :" + buildProperties.getName() +" - Version: " + buildProperties.getVersion());		
		return builder.toString();
	}
}
