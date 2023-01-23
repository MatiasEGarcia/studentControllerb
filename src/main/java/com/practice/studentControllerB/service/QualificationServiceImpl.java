package com.practice.studentControllerB.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.studentControllerB.dao.CourseDao;
import com.practice.studentControllerB.dao.QualificationDao;
import com.practice.studentControllerB.dao.StudentDao;
import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Qualification;
import com.practice.studentControllerB.model.Student;
import com.practice.studentControllerB.utils.MessagesProp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QualificationServiceImpl implements QualificationService{
	
	private final QualificationDao qualificationDao;
	private final MessagesProp messagesProp;
	private final CourseDao courseDao;
	private final StudentDao studentDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Qualification> getAll() {
		List<Qualification> qualifications = qualificationDao.getAll();
		if(qualifications != null) return Collections.unmodifiableList(qualifications); 
		return null;
	}

	@Override
	@Transactional
	public int create(Qualification t) {
		if(t == null) throw new IllegalArgumentException(messagesProp.getMessage("qualification-not-null"));
		this.studentAndCourseExist(t);
		return qualificationDao.create(t);
	}

	@Override
	@Transactional(readOnly=true)
	public Qualification getById(Long id) {
		return qualificationDao.getById(id);
	}

	@Override
	@Transactional
	public int update(Qualification t) {
		if(t == null) throw new IllegalArgumentException(messagesProp.getMessage("qualification-not-null"));
		if(qualificationDao.getById(t.getId()) == null) throw new IllegalArgumentException(messagesProp.getMessage("qualification-id-not-found"));
		this.studentAndCourseExist(t);
		return qualificationDao.update(t);
	}

	@Override
	@Transactional
	public int delete(Long id) {
		if(qualificationDao.getById(id) == null) throw new IllegalArgumentException(messagesProp.getMessage("qualification-id-not-found"));
		return qualificationDao.delete(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Qualification> getByCourseId(Long course) {
		if(courseDao.getById(course) == null) throw new IllegalArgumentException(messagesProp.getMessage("course-id-not-found"));
		List<Qualification> qualifications = qualificationDao.getByCourseId(course);
		if(qualifications != null) return Collections.unmodifiableList(qualifications);
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Qualification> getByStudentId(Long student) {
		if(studentDao.getById(student) == null) throw new IllegalArgumentException(messagesProp.getMessage("student-id-not-found"));
		List<Qualification> qualifications = qualificationDao.getByStudentId(student);
		if(qualifications != null) return Collections.unmodifiableList(qualifications);
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public void studentAndCourseExist(Qualification qualification) {
		Course course = courseDao.getById(qualification.getCourse().getId());
		Student student = studentDao.getById(qualification.getStudent().getId());
		if(course == null || student == null)throw new IllegalArgumentException(messagesProp.getMessage("qualification-course-student-no-found"));
	}

}
