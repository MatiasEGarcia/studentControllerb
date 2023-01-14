package com.practice.studentControllerB.service;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.studentControllerB.dao.TeacherDao;
import com.practice.studentControllerB.model.Teacher;

import lombok.RequiredArgsConstructor;

@PropertySource("classpath:application-messages.properties")
@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService{

	private final TeacherDao teacherDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Teacher> getAll() {
		List<Teacher> teachers = teacherDao.getAll();
		if(teachers != null) return Collections.unmodifiableList(teachers);
		return teachers;
	}

	@Override
	public int create(Teacher t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public Teacher getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Teacher t) {
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
	public List<Teacher> getByQualification(String qualification) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Teacher> getByNationality(String nationality) {
		// TODO Auto-generated method stub
		return null;
	}

}
