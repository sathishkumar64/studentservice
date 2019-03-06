package com.studentservice.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)	
public class StudentAppData {

	
	private String studentAppInfo;	

	private String message;
	
	private List<Student> listStudent;
	
	public List<Student> getListStudent() {
		return listStudent;
	}

	public void setListStudent(List<Student> listStudent) {
		this.listStudent = listStudent;
	}
	
	public String getStudentAppInfo() {
		return studentAppInfo;
	}

	public void setStudentAppInfo(String studentAppInfo) {
		this.studentAppInfo = studentAppInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
