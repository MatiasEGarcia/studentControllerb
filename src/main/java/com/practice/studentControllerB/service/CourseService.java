package com.practice.studentControllerB.service;

import java.util.List;

import com.practice.studentControllerB.model.Course;

public interface CourseService extends GenericService<Course> {

	Course getByTitle(String title);

	List<Course> getByShift(String shift);

	Course getByTeacherId(Long teacher);
	
	void teacherNullExist(Course course);
}
