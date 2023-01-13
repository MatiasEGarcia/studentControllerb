package com.practice.studentControllerB.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.studentControllerB.dao.StudentDao;
import com.practice.studentControllerB.model.Student;

import lombok.RequiredArgsConstructor;

@PropertySource("/exception-messages.properties")
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService{

	private final StudentDao studentDao; 
	
	@Value("${e.student.must.not.be.null}")
	private String studentNotNull;

	@Value("${e.student.email.already.used}")
	private String studentEmailAlreadyUsed;
	
	@Override
	@Transactional(readOnly = true)
	public List<Student> getAll() {
		List<Student> students = studentDao.getAll();
		if(students == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(students);
	}

	@Override
	public int create(Student student) {
		if(student == null) throw new IllegalArgumentException(studentNotNull);
		if(studentDao.getByEmail(student.getEmail()) != null) throw new IllegalArgumentException(studentEmailAlreadyUsed);
		return studentDao.create(student);
	}

	@Override
	@Transactional(readOnly = true)
	public Student getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Student t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public Student getByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> favoriteLanguage(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
