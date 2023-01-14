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

@PropertySource("classpath:application-messages.properties")
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService{

	private final StudentDao studentDao; 
	
	@Value("${e.student.must.not.be.null}")
	private String studentNotNull;

	@Value("${e.student.email.already.used}")
	private String studentEmailAlreadyUsed;
	
	@Value("${e.student.id.not.found}")
	private String studentIdNotFound;
	
	@Override
	@Transactional(readOnly = true)
	public List<Student> getAll() {
		List<Student> students = studentDao.getAll();
		if(students != null) return Collections.unmodifiableList(students);
		return students; //can return null list
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
		return studentDao.getById(id);
	}

	@Override
	public int update(Student student) {
		if(student == null) throw new IllegalArgumentException(studentNotNull);
		if(studentDao.getById(student.getId()) == null) throw new IllegalArgumentException(studentIdNotFound);
		return studentDao.update(student);
	}

	@Override
	public int delete(Long id) {
		if(studentDao.getById(id) == null) throw new IllegalArgumentException(studentIdNotFound);
		return studentDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Student getByEmail(String email) {
		return studentDao.getByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> favoriteLanguage(String language) {
		return studentDao.getByfavoriteLanguage(language);
	}

}
