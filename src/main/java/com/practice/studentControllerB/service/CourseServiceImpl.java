package com.practice.studentControllerB.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.studentControllerB.dao.CourseDao;
import com.practice.studentControllerB.dao.TeacherDao;
import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Shift;
import com.practice.studentControllerB.utils.MessagesProp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
	
	private final MessagesProp messagesProp;
	private final CourseDao courseDao;
	private final TeacherDao teacherDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Course> getAll() {
		List<Course> courses = courseDao.getAll();
		if(courses != null) return Collections.unmodifiableList(courses);
		return null;
	}

	@Override
	@Transactional
	public int create(Course t) {
		if(t == null) throw new IllegalArgumentException(messagesProp.getMessage("course-not-null"));
		this.teacherNullExist(t);
		if(courseDao.getByTitle(t.getTitle()) != null) throw new IllegalArgumentException(messagesProp.getMessage("course-title-already-used"));
		return courseDao.create(t);
	}

	@Override
	@Transactional(readOnly = true)
	public Course getById(Long id) {
		return courseDao.getById(id);
	}

	@Override
	@Transactional
	public int update(Course t) {
		if(t == null) throw new IllegalArgumentException(messagesProp.getMessage("course-not-null"));
		this.teacherNullExist(t);
		if(courseDao.getById(t.getId()) == null) throw new IllegalArgumentException(messagesProp.getMessage("course-id-not-found"));
		if(courseDao.getByTitle(t.getTitle()) != null) throw new IllegalArgumentException(messagesProp.getMessage("course-title-already-used"));
		return courseDao.update(t);
	}

	@Override
	@Transactional
	public int delete(Long id) {
		if(courseDao.getById(id) == null) throw new IllegalArgumentException(messagesProp.getMessage("course-id-not-found"));
		return courseDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Course getByTitle(String title) {
		return courseDao.getByTitle(title);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Course> getByShift(String shift) {
		boolean flag = false;
		Shift[] shifts = Shift.values();
		List<Course> courses;
		for(Shift s : shifts) {
			if(s.sameThan(shift)) {
				flag = true;
				break;
			}
		}
		if(flag == false) throw new IllegalArgumentException(messagesProp.getMessage("shift-not-found"));
		courses = courseDao.getByShift(shift.toUpperCase());
		if(courses != null) return Collections.unmodifiableList(courses);
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Course getByTeacherId(Long teacher) {
		if(teacherDao.getById(teacher) == null) throw new IllegalArgumentException(messagesProp.getMessage("teacher-id-not-found"));
		return courseDao.getByTeacherId(teacher);
	}

	//This method is to check if teacher is not null and if exist, so you can save your course with this teacher without problem
	@Override
	@Transactional(readOnly = true)
	public void teacherNullExist(Course course) {
		if(course.getTeacher() == null) throw new IllegalArgumentException(messagesProp.getMessage("teacher-not-null"));
		if(teacherDao.getById(course.getTeacher().getId()) == null) throw new IllegalArgumentException(messagesProp.getMessage("teacher-id-not-found"));
	}
}
