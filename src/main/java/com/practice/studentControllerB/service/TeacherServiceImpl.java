package com.practice.studentControllerB.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.studentControllerB.dao.TeacherDao;
import com.practice.studentControllerB.model.QualificationE;
import com.practice.studentControllerB.model.Teacher;
import com.practice.studentControllerB.utils.MessagesProp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService{

	private final TeacherDao teacherDao;
	private final MessagesProp messagesProp;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Teacher> getAll() {
		List<Teacher> teachers = teacherDao.getAll();
		if(teachers != null) return Collections.unmodifiableList(teachers);
		return null;
	}

	@Override
	@Transactional
	public int create(Teacher t) {
		if(t == null) throw new IllegalArgumentException(messagesProp.getMessage("teacher-not-null"));
		if(teacherDao.getByEmail(t.getEmail()) != null) throw new IllegalArgumentException(messagesProp.getMessage("teacher-email-already-used")); 
		return teacherDao.create(t);
	}

	@Override
	@Transactional(readOnly = true)
	public Teacher getById(Long id) {
		return teacherDao.getById(id);
	}

	@Override
	@Transactional
	public int update(Teacher t) {
		if(t == null) throw new IllegalArgumentException(messagesProp.getMessage("teacher-not-null"));
		if(teacherDao.getById(t.getId()) == null) throw new IllegalArgumentException(messagesProp.getMessage("teacher-id-not-found"));
		return teacherDao.update(t);
	}

	@Override
	@Transactional
	public int delete(Long id) {
		if(teacherDao.getById(id) == null) throw new IllegalArgumentException(messagesProp.getMessage("teacher-id-not-found"));
		return teacherDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Teacher> getByQualification(String qualification) {
		boolean flag = false;
		QualificationE[] quali = QualificationE.values();
		for(QualificationE q : quali) {
			if(q.sameThan(qualification)) {
				flag = true;
				break;
			}
		}
		if(flag == false) throw new IllegalArgumentException(messagesProp.getMessage("qualification-not-found"));
		List<Teacher> teachers = teacherDao.getByQualification(qualification.toUpperCase());
		if(teachers != null) return Collections.unmodifiableList(teachers);
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Teacher> getByNationality(String nationality) {
		List<Teacher> teachers = teacherDao.getByNationality(nationality);
		if(teachers != null) return Collections.unmodifiableList(teachers);
		return null;
	}

}
