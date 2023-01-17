package com.practice.studentControllerB.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.studentControllerB.config.prop.ExceptionProp;
import com.practice.studentControllerB.dao.QualificationDao;
import com.practice.studentControllerB.model.Qualification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QualificationServiceImpl implements QualificationService{
	
	private final QualificationDao qualificationDao;
	private final ExceptionProp excepProp;
	
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
		if(t == null) throw new IllegalArgumentException(excepProp.getQualificationNotNull());
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
		if(t == null) throw new IllegalArgumentException(excepProp.getQualificationNotNull());
		if(qualificationDao.getById(t.getId()) == null) throw new IllegalArgumentException(excepProp.getQualificationIdNotFound());
		return qualificationDao.update(t);
	}

	@Override
	@Transactional
	public int delete(Long id) {
		if(qualificationDao.getById(id) == null) throw new IllegalArgumentException(excepProp.getQualificationIdNotFound());
		return qualificationDao.delete(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Qualification> getByCourseId(Long course) {
		List<Qualification> qualifications = qualificationDao.getByCourseId(course);
		if(qualifications != null) return Collections.unmodifiableList(qualifications);
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Qualification> getByStudentId(Long student) {
		List<Qualification> qualifications = qualificationDao.getByStudentId(student);
		if(qualifications != null) return Collections.unmodifiableList(qualifications);
		return null;
	}

}
