package com.studentservice.repositories;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;

import com.studentservice.domain.Student;
import com.studentservice.domain.StudentAppData;
import com.studentservice.util.GoogleZoneFinder;

@Repository
public class MongoDBStudentRepository implements StudentRepository{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final MongoOperations operations;	

	@Autowired
	BuildProperties buildProperties;

	//@Autowired
   // private JmsTemplate jmsTemplate;
	
	
	//@Autowired
  //  private Queue queue;
	
	@Autowired
	private GoogleZoneFinder googleZoneFinder;
	
	
	
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
		
		googleZoneFinder.printInstances();
		
		
		
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
		
		
		
		Locale currentLocale = Locale.getDefault();
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		ZoneId zone = zonedDateTime.getZone();
		String formattedString2 = zonedDateTime.format(formatter2);
		
		
		StringBuilder st=new StringBuilder();
		st.append(currentLocale)
		.append("<<<>>"+zonedDateTime)
		.append("<<<>>"+zone.getId())		
		.append("<<<>>"+zonedDateTime.getOffset())
		.append("<<<>>"+formattedString2);
		
		
		
		
		appData.setCountryCode(st.toString());
		sendJmsMessage(schoolName);		
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
		return operations.findAll(Student.class);
	}

	
	private void sendJmsMessage(String schoolname){		
		 Instant instant = Instant.now(); 		
		 StringBuilder sendingMessage=new StringBuilder();
		 sendingMessage.append("Posting JMS Student details for ");
		 sendingMessage.append(schoolname);
		 sendingMessage.append(" and time is ");
		 sendingMessage.append(instant);
	//	 jmsTemplate.convertAndSend(queue,sendingMessage.toString());		
		 logger.info("JMS Posting Message............... {} ",sendingMessage.toString());	
	}
	
	
	private String setBuildInfo(){		
		StringBuilder builder=new StringBuilder();
		builder.append("Application Name :" + buildProperties.getName() +" - Version: " + buildProperties.getVersion());		
		return builder.toString();
	}
}
