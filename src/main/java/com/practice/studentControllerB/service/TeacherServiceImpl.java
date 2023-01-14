package com.practice.studentControllerB.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${e.teacher.must.not.be.null}")
	private String teacherNotNull;

	@Value("${e.teacher.email.already.used}")
	private String teacherEmailAlreadyUsed;
	
	@Value("${e.teacher.id.not.found}")
	private String teacherIdNotFound;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Teacher> getAll() {
		List<Teacher> teachers = teacherDao.getAll();
		if(teachers != null) return Collections.unmodifiableList(teachers);
		return teachers;
	}

	@Override
	public int create(Teacher t) {
		if(t == null) throw new IllegalArgumentException(teacherNotNull);
		if(teacherDao.getByEmail(t.getEmail()) != null) throw new IllegalArgumentException(teacherEmailAlreadyUsed); 
		return teacherDao.create(t);
	}

	@Override
	@Transactional(readOnly = true)
	public Teacher getById(Long id) {
		return teacherDao.getById(id);
	}

	@Override
	public int update(Teacher t) {
		if(t == null) throw new IllegalArgumentException(teacherNotNull);
		if(teacherDao.getById(t.getId()) == null) throw new IllegalArgumentException(teacherIdNotFound);
		return teacherDao.update(t);
	}

	@Override
	public int delete(Long id) {
		if(teacherDao.getById(id) == null) throw new IllegalArgumentException(teacherIdNotFound);
		return teacherDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Teacher> getByQualification(String qualification) {
		List<Teacher> teachers = teacherDao.getByQualification(qualification);
		if(teachers != null) return Collections.unmodifiableList(teachers);
		return teachers;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Teacher> getByNationality(String nationality) {
		List<Teacher> teachers = teacherDao.getByNationality(nationality);
		if(teachers != null) return Collections.unmodifiableList(teachers);
		return teachers;
	}

}
