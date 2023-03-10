package com.practice.studentControllerB.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.studentControllerB.dao.StudentDao;
import com.practice.studentControllerB.model.Student;
import com.practice.studentControllerB.utils.MessagesProp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService{

	private final StudentDao studentDao; 
	private final MessagesProp messagesProp;
	
	@Override
	@Transactional(readOnly = true)
	public List<Student> getAll() {
		List<Student> students = studentDao.getAll();
		if(students != null) return Collections.unmodifiableList(students);
		return null;
	}

	@Override
	@Transactional
	public int create(Student student) {
		if(student == null) throw new IllegalArgumentException(messagesProp.getMessage("student-not-null"));
		if(studentDao.getByEmail(student.getEmail()) != null) throw new IllegalArgumentException(messagesProp.getMessage("student-email-already-used"));
		return studentDao.create(student);
	}

	@Override
	@Transactional(readOnly = true)
	public Student getById(Long id) {
		return studentDao.getById(id);
	}

	@Override
	@Transactional
	public int update(Student student) {
		if(student == null) throw new IllegalArgumentException(messagesProp.getMessage("student-not-null"));
		if(studentDao.getById(student.getId()) == null) throw new IllegalArgumentException(messagesProp.getMessage("student-id-not-found"));
		return studentDao.update(student);
	}

	@Override
	@Transactional
	public int delete(Long id) {
		if(studentDao.getById(id) == null) throw new IllegalArgumentException(messagesProp.getMessage("student-id-not-found"));
		return studentDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Student getByEmail(String email) {
		return studentDao.getByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> getByFavoriteLanguage(String language) {
		List<Student> students = studentDao.getByfavoriteLanguage(language);
		if(students != null) return Collections.unmodifiableList(students);
		return null;
	}

}
