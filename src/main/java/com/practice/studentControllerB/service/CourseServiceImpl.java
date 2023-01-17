package com.practice.studentControllerB.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.studentControllerB.config.prop.ExceptionProp;
import com.practice.studentControllerB.dao.CourseDao;
import com.practice.studentControllerB.dao.TeacherDao;
import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Shift;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
	
	private final CourseDao courseDao;
	private final TeacherDao teacherDao;
	private final ExceptionProp excepProp;
	
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
		if(t == null) throw new IllegalArgumentException(excepProp.getCourseNotnull());
		if(courseDao.getByTitle(t.getTitle()) != null) throw new IllegalArgumentException(excepProp.getCourseTitleAlreadyUsed());
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
		if(t == null) throw new IllegalArgumentException(excepProp.getCourseNotnull());
		if(courseDao.getById(t.getId()) == null) throw new IllegalArgumentException(excepProp.getCourseIdNotFound());
		return courseDao.update(t);
	}

	@Override
	@Transactional
	public int delete(Long id) {
		if(courseDao.getById(id) == null) throw new IllegalArgumentException(excepProp.getCourseIdNotFound());
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
		if(flag == false) throw new IllegalArgumentException(excepProp.getShiftNotFound());
		courses = courseDao.getByShift(shift.toUpperCase());
		if(courses != null) return Collections.unmodifiableList(courses);
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Course getByTeacherId(Long teacher) {
		if(teacherDao.getById(teacher) == null) throw new IllegalArgumentException(excepProp.getTeacherIdNotFound());
		return courseDao.getByTeacherId(teacher);
	}


	
}
