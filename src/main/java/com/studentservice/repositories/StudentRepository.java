package com.studentservice.repositories;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.studentservice.domain.StudentAppData;
import com.studentservice.domain.Student;

public interface StudentRepository extends Repository<Student, Long> {
	
	StudentAppData save(Student student);
	
	List<Student> findAll();
	
    StudentAppData findByschoolname(String schoolName);
}
